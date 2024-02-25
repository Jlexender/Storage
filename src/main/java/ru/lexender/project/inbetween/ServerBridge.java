package ru.lexender.project.inbetween;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.client.io.Input;
import ru.lexender.project.server.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * client-server
 */

@Getter
public class ServerBridge {
    public static final Logger logger = LoggerFactory.getLogger(ServerBridge.class);

    private final Server server;
    private final int port;

    public ServerBridge(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    public void run() {
        try (ServerSocketChannel channel = ServerSocketChannel.open()) {
            SocketAddress address = new InetSocketAddress(port);
            channel.bind(address);

            channel.configureBlocking(false);
            logger.info("Blocking mode {}", channel.isBlocking());

            Selector serverSelector = Selector.open();
            channel.register(serverSelector, SelectionKey.OP_ACCEPT);

            logger.info("Started on port {}", port);

            for (;;) {
                SocketChannel accepted = channel.accept();
                if (accepted != null) {
                    logger.info("New channel {}", accepted);

                    Response response;
                    do {
                        Request query = getRequest(accepted);
                        logger.info("Received {}", query);
                        response = server.getRequest(query);
                        logger.info("Generated {}", query);
                        sendResponse(accepted, response);
                        logger.info("{} sent to {}", response, accepted);
                    } while (response.getPrompt() != Prompt.DISCONNECTED);
                    logger.info("Closing connection " + accepted);
                    accepted.close();
                }
            }
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }
    }


    public Request getRequest(SocketChannel channel) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(channel.socket().getInputStream());
            return (Request) objectInputStream.readObject();
        } catch (Exception exception) {
            logger.error(String.format("Unable to get request from %s, using empty request", channel));
            return new Request(new Input("") {
                @Override
                public String get() {
                    return "";
                }
            });
        }
    }

    public void sendResponse(SocketChannel channel, Response response) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        } catch (IOException exception) {
            logger.error(String.format("Unable to send response %s to %s", response, channel));
        }
    }

}