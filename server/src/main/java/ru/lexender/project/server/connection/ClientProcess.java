package ru.lexender.project.server.connection;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.Server;

import java.net.Socket;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ClientProcess implements Runnable {
    private final ServerBridge bridge;
    private final Socket client;

    public ClientProcess(ServerBridge bridge, Socket client) {
        this.client = client;
        this.bridge = bridge;
    }

    public void run() {
        try {
            bridge.sendResponse(client, new Response(Prompt.CONNECTED, ServerBridge.helloString));
            while (client.isConnected()) {
                Future<Request> requestFuture = bridge.getReadingPool().submit(() -> bridge.getRequest(client));

                Request request = requestFuture.get();
                String username = request.getUserdata().getUsername();
                Server.logger.info("Got request {}", requestFuture.get());

                Response response;
                if (bridge.getResponseQueue().get(username) == null
                || bridge.getResponseQueue().get(username).isEmpty()) {
                    ServerBridge.logger.info("Response queue is empty for {}, handling request",
                            request.getUserdata().getUsername());
                    response = bridge.getHandlingPool().invoke(new HandleTask(requestFuture.get()));
                } else {
                    ServerBridge.logger.info("Response queue is NOT empty, skipping handling");
                    response = bridge.getResponseQueue().get(username).remove();
                }

                bridge.getLastResponse().put(username, response);
                new Thread(new SendProcess(response), "SendProcessThread").start();
            }
        } catch (Exception exception) {
            ServerBridge.logger.error(exception.getMessage());
        }
    }

    private class SendProcess implements Runnable {
        private final Response response;

        public SendProcess(Response response) {
            this.response = response;
        }

        public void run() {
            bridge.sendResponse(client, response);
            ServerBridge.logger.info("Sent {} to {}", response, client);
        }
    }

    private class HandleTask extends RecursiveTask<Response> {
        private final Request request;

        public HandleTask(Request request) {
            this.request = request;
        }

        public Response compute() {
            return bridge.getServer().handle(request);
        }
    }
}
