package ru.lexender.project.server.connection;

import ru.lexender.project.inbetween.Request;
import ru.lexender.project.server.Server;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestProcessor {
    private final ExecutorService cachedThreadPool;
    private final ServerBridge bridge;

    public RequestProcessor(ServerBridge bridge) {
        cachedThreadPool = Executors.newCachedThreadPool();


        this.bridge = bridge;
    }

    public Future<Request> processRequest(SocketChannel client) {
        return cachedThreadPool.submit(() -> {
            Request request = bridge.getRequest(client);
            Server.logger.info("Got request {}", request);
            return request;
        });
    }

}
