package ru.lexender.project.server.connect;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.handler.IHandle;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class RequestProcessor {
    private final ExecutorService cachedThreadPool;
    private final ForkJoinPool forkJoinPool;
    private final ServerBridge bridge;

    public RequestProcessor(ServerBridge bridge) {
        cachedThreadPool = Executors.newCachedThreadPool();
        forkJoinPool = new ForkJoinPool();
        this.bridge = bridge;
    }

    public Request processRequest(SocketChannel client) {
        Request request = bridge.getRequest(client);
        Server.logger.info("Got request {}", request);
        return request;
    }


}
