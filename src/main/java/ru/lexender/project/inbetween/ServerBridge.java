package ru.lexender.project.inbetween;

import ru.lexender.project.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * client-server
 */

public class ServerBridge extends Thread {
    private final Server server;
    private final int port;

    public ServerBridge(Server server, int port) {
        this.server = server;
        this.port = port;
    }


    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.printf("Server listening on port %d\n", port);
            while (true) {
                Socket socket = serverSocket.accept();

                //
                // Handle client actions
                //

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
