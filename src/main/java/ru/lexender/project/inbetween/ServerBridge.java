package ru.lexender.project.inbetween;

import lombok.Getter;
import ru.lexender.project.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * client-server
 */

@Getter
public class ServerBridge {
    private final Server server;
    private final int port;
    private final ServerSocketChannel channel;

    public ServerBridge(Server server, int port) throws IOException {
        this.server = server;
        this.port = port;

        SocketAddress address = new InetSocketAddress(port);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);

        this.channel = serverSocketChannel;
    }

    public void run() {
        try {
            System.out.printf("Server listening on port %d\n", port);
            for (;;) {
                SocketChannel socketChannel = channel.accept();
                Session session = new Session(server, socketChannel);
                session.start();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


}