package ru.lexender.project.server.connection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.Server;

import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Getter
public class ResponseProcessor {
    public static final Logger logger = LoggerFactory.getLogger(ServerBridge.class);
    private final Map<String, Response> lastResponses;
    private final Map<String, Queue<Response>> responseQueue;
    private final ServerBridge bridge;
    private final ForkJoinPool forkJoinPool;

    public ResponseProcessor(ServerBridge bridge) {
        this.responseQueue = new HashMap<>();
        this.lastResponses = new HashMap<>();
        this.bridge = bridge;

        forkJoinPool = new ForkJoinPool();
    }

    public Response handleRequest(Request request) {
        RecursiveTask<Response> task = new HandlerRequestTask(request);
        return forkJoinPool.invoke(task);
    }

    public void sendResponse(SocketChannel client, Response response, String username) {
        Thread thread = new Thread(new SendResponseTask(client, response, username), "response-manager");
        thread.start();
    }

    public void queryResponse(String username, Response response) {
        if (responseQueue.get(username) == null)
            responseQueue.put(username, new ArrayDeque<>());
        responseQueue.get(username).add(response);
        logger.debug("Queried response {} for username {}", response, username);
    }

    public Response getLastResponse(String username) {
        return lastResponses.get(username);
    }

    public boolean isResponseQueueEmptyFor(String username) {
        return responseQueue.get(username) == null || responseQueue.get(username).isEmpty();
    }

    @AllArgsConstructor
    private class SendResponseTask implements Runnable {
        private final SocketChannel client;
        private final Response response;
        private final String username;

        public void run() {
            bridge.sendResponse(client, response);
            lastResponses.put(username, response);
            logger.info("Sent {} to {} by username {}", response, client, username);
        }
    }

    private class HandlerRequestTask extends RecursiveTask<Response> {
        private final Request request;

        HandlerRequestTask(Request request) {
            this.request = request;
        }

        @Override
        protected Response compute() {
            Optional<Request> requestOptional = Optional.ofNullable(request);
            if (requestOptional.isEmpty()) return new Response(Prompt.UNEXPECTED_ERROR);

            Request request = requestOptional.get();

            Response response;
            String username = request.getUserdata().getUsername();
            if (isResponseQueueEmptyFor(username)) {
                Server.logger.info("Response queue is empty for {}, handling request",
                        request.getUserdata().getUsername());
                response = bridge.getServer().handle(request, username);
            } else {
                logger.info("Response queue is NOT empty, skipping handling");
                response = getResponseQueue().get(username).remove();
            }
            return response;
        }
    }
}