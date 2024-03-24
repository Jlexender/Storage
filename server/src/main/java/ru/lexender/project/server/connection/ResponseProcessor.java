package ru.lexender.project.server.connect;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

@Getter
public class ResponseProcessor {
    public static final Logger logger = LoggerFactory.getLogger(ServerBridge.class);
    private final Map<String, Response> lastResponses;
    private final Map<String, Queue<Response>> responseQueue;
    private final ServerBridge bridge;

    public ResponseProcessor(ServerBridge bridge) {
        this.responseQueue = new HashMap<>();
        this.lastResponses = new HashMap<>();
        this.bridge = bridge;
    }

    public Response handleRequest(SocketChannel client, Request request) throws IOException {
        Optional<Request> requestOptional = Optional.ofNullable(request);
        if (requestOptional.isEmpty()) return new Response(Prompt.UNEXPECTED_ERROR);

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

    public void sendResponse(SocketChannel client, Response response, String username) throws IOException {
        bridge.sendResponse(client, response);
        lastResponses.put(username, response);
        logger.info("Sent {} to {} by username {}", response, client, username);
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
}