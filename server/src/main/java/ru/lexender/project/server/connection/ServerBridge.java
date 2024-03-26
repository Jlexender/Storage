package ru.lexender.project.server.connection;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.ServerConsole;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * client-server
 * NOTE: 1 request = 1 response!
 */

@Getter
public class ServerBridge {
    public static final String helloString = "Connection has been established.";

    public static final Logger logger = LoggerFactory.getLogger(ServerBridge.class);

    private final Server server;
    private final int port;
    private final ForkJoinPool handlingPool;
    private final ExecutorService readingPool;
    private final Map<String, Response> lastResponse;
    private final Map<String, Queue<Response>> responseQueue;

    public ServerBridge(Server server, int port) {
        this.server = server;
        this.port = port;
        this.handlingPool = new ForkJoinPool();
        this.readingPool = Executors.newCachedThreadPool();
        this.lastResponse = new HashMap<>();
        this.responseQueue = new HashMap<>();
    }

    public void run() {
        Thread console = new Thread(new ServerConsole(this), "serverside");
        console.start();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started on port {}", port);
            while (true) {
                Socket client = serverSocket.accept();
                logger.info("New client {} registered", client);
                new Thread(new ClientProcess(this, client)).start();
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

    public void sendResponse(Socket client, Response response) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(response);
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }
    }

    public void queryResponse(String username, Response response) {
        if (responseQueue.get(username) == null)
            responseQueue.put(username, new ArrayDeque<>());
        responseQueue.get(username).add(response);
        logger.debug("Queried response {} for username {}", response, username);
    }

    public Request getRequest(Socket client) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
            return (Request) objectInputStream.readObject();
        } catch (Exception exception) {
            return null;
        }
    }

}