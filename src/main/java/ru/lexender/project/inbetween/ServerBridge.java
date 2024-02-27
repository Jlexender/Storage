package ru.lexender.project.inbetween;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.server.Server;

import java.io.ByteArrayInputStream;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * client-server
 */

@Getter
public class ServerBridge {
    private static final String putin = """
                            ⣿⣿⣿⣿⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                            ⣿⣿⣿⣵⣿⣿⣿⠿⡟⣛⣧⣿⣯⣿⣝⡻⢿⣿⣿⣿⣿⣿⣿⣿
                            ⣿⣿⣿⣿⣿⠋⠁⣴⣶⣿⣿⣿⣿⣿⣿⣿⣦⣍⢿⣿⣿⣿⣿⣿
                            ⣿⣿⣿⣿⢷⠄⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣏⢼⣿⣿⣿⣿
                            ⢹⣿⣿⢻⠎⠔⣛⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⣿⣿⣿⣿
                            ⢸⣿⣿⠇⡶⠄⣿⣿⠿⠟⡛⠛⠻⣿⡿⠿⠿⣿⣗⢣⣿⣿⣿⣿
                            ⠐⣿⣿⡿⣷⣾⣿⣿⣿⣾⣶⣶⣶⣿⣁⣔⣤⣀⣼⢲⣿⣿⣿⣿
                            ⠄⣿⣿⣿⣿⣾⣟⣿⣿⣿⣿⣿⣿⣿⡿⣿⣿⣿⢟⣾⣿⣿⣿⣿
                            ⠄⣟⣿⣿⣿⡷⣿⣿⣿⣿⣿⣮⣽⠛⢻⣽⣿⡇⣾⣿⣿⣿⣿⣿
                            ⠄⢻⣿⣿⣿⡷⠻⢻⡻⣯⣝⢿⣟⣛⣛⣛⠝⢻⣿⣿⣿⣿⣿⣿
                            ⠄⠸⣿⣿⡟⣹⣦⠄⠋⠻⢿⣶⣶⣶⡾⠃⡂⢾⣿⣿⣿⣿⣿⣿
                            ⠄⠄⠟⠋⠄⢻⣿⣧⣲⡀⡀⠄⠉⠱⣠⣾⡇⠄⠉⠛⢿⣿⣿⣿
                            ⠄⠄⠄⠄⠄⠈⣿⣿⣿⣷⣿⣿⢾⣾⣿⣿⣇⠄⠄⠄⠄⠄⠉⠉
                            ⠄⠄⠄⠄⠄⠄⠸⣿⣿⠟⠃⠄⠄⢈⣻⣿⣿⠄⠄⠄⠄⠄⠄⠄
                            ⠄⠄⠄⠄⠄⠄⠄⢿⣿⣾⣷⡄⠄⢾⣿⣿⣿⡄⠄⠄⠄⠄⠄⠄
                            ⠄⠄⠄⠄⠄⠄⠄⠸⣿⣿⣿⠃⠄⠈⢿⣿⣿⠄⠄⠄⠄⠄⠄⠄
                            *** best handshake ever ***
                            """;

    public static final Logger logger = LoggerFactory.getLogger(ServerBridge.class);

    private final Server server;
    private final int port;

    public ServerBridge(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    public void run() {
        server.getConsole().start();

        try (ServerSocketChannel channel = ServerSocketChannel.open()) {
            SocketAddress address = new InetSocketAddress(port);
            channel.bind(address);

            channel.configureBlocking(false);
            logger.info("Blocking mode {}", channel.isBlocking());

            Selector serverSelector = Selector.open();
            channel.register(serverSelector, SelectionKey.OP_ACCEPT);

            logger.info("Started on port {}", port);
            Map<SelectionKey, SocketChannel> connections = new HashMap<>();
            for (;;) {
                serverSelector.select();

                Set<SelectionKey> keys = serverSelector.selectedKeys();
                logger.info("{}", keys);
                for (SelectionKey key: keys) {
                    if (key.isAcceptable()) {
                        SocketChannel accepted = channel.accept();
                        connections.put(key, accepted);
                        logger.info("Accepted new connection {}", accepted);
                        sendResponse(accepted, new Response(Prompt.CONNECTED, putin));
                    }

                    SocketChannel accepted = connections.get(key);
                    Request query = getRequest(accepted);
                    if (query != null) {
                        logger.info("Received {}", query);
                        Response response = server.getRequest(query);

                        logger.info("Generated {}", query);
                        try {
                            sendResponse(connections.get(key), response);
                        } catch (IOException exception) {
                            logger.error("Unable to send response {} to {}, closing...", response, channel);
                            accepted.close();
                        }
                    }
                }
            }

        } catch (IOException | IllegalArgumentException exception) {
            logger.error(exception.getMessage());
        }
    }


    public Request getRequest(SocketChannel channel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer);
            buffer.flip();
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(arrayInputStream);
            return (Request) objectInputStream.readObject();
        } catch (Exception exception) {
            return null;
        }
    }

    public void sendResponse(SocketChannel channel, Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
    }

}