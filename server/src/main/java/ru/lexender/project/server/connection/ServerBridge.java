package ru.lexender.project.server.connection;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.ServerConsole;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * client-server
 * NOTE: 1 request = 1 response!
 */

@Getter
public class ServerBridge {
    private static final String helloString = "Connection has been established.";

    public static final Logger logger = LoggerFactory.getLogger(ServerBridge.class);

    private final Server server;
    private final int port;
    private final ResponseProcessor responseProcessor;
    private final RequestProcessor requestProcessor;

    public ServerBridge(Server server, int port) {
        this.server = server;
        this.port = port;
        responseProcessor = new ResponseProcessor(this);
        requestProcessor = new RequestProcessor(this);
    }

    public void run() {
        Thread console = new Thread(new ServerConsole(this), "serverside");
        console.start();

        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(false);

            logger.info("Server started on port {}", port);

            Selector selector = Selector.open();
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel client = serverSocket.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        logger.info("New channel {} registered", client);

                        sendResponse(client, new Response(Prompt.CONNECTED, helloString));
                        logger.info("Sent handshake response to {}", client);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        Request request = requestProcessor.processRequest(client).get();
                        if (request != null) {
                            Response response = responseProcessor.handleRequest(request);
                            responseProcessor.sendResponse(client, response, request.getUserdata().getUsername());
                        } else {
                            logger.info("Closing connection {}", client);
                            client.close();
                        }
                    }
                }
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

    public void sendResponse(SocketChannel channel, Response response) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            while (buffer.hasRemaining())
                channel.write(buffer);
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }

    }

    public Request getRequest(SocketChannel channel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                return null;
            }

            buffer.flip();
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(arrayInputStream);
            return (Request) objectInputStream.readObject();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}