package ru.lexender.project.inbetween;

import ru.lexender.project.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * client-server
 */

public class ServerBridge extends Thread {
    private final Server server;
    private final int port;
    private final ServerSocket socket;

    public ServerBridge(Server server, int port) throws IOException {
        this.server = server;
        this.port = port;
        socket = new ServerSocket(port);
    }

    public void send(Response response, Socket socket) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(response);
        } catch (IOException exception) {
            // logs
        }
    }

    public Request get(Socket socket) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (Request) inputStream.readObject();
        } catch (Exception exception) {
            return null;
        }
    }


    public void run() {
        try {
            System.out.printf("Server listening on port %d\n", port);
            for (;;) {
                Socket acceptedSocket = socket.accept();
                Response response;
                do {
                    Request deserialized = get(acceptedSocket);
                    response = server.getRequest(deserialized);
                    send(response, acceptedSocket);
                } while (response.getPrompt() != Prompt.DISCONNECTED);

                acceptedSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
