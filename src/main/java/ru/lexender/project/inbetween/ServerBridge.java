package ru.lexender.project.inbetween;

import lombok.Getter;
import ru.lexender.project.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * client-server
 */

@Getter
public class ServerBridge {
    private final Server server;
    private final int port;
    private final ServerSocket socket;

    public ServerBridge(Server server, int port) throws IOException {
        this.server = server;
        this.port = port;
        socket = new ServerSocket(port);
    }

    public void run() {
        try {
            System.out.printf("Server listening on port %d\n", port);
            for (;;) {
                Socket acceptedSocket = socket.accept();
                SocketHandler handler = new SocketHandler(this, acceptedSocket);
                handler.start();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
