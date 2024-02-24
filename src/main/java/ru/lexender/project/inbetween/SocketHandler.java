package ru.lexender.project.inbetween;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * client-server
 */


public class SocketHandler extends Thread {
    private final ServerBridge serverBridge;
    private final Socket socket;

    public SocketHandler(ServerBridge serverBridge, Socket socket) {
        this.serverBridge = serverBridge;
        this.socket = socket;
    }

    public Request get(Socket socket) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (Request) inputStream.readObject();
        } catch (Exception exception) {
            return null;
        }
    }

    public void send(Response response, Socket socket) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(response);
        } catch (IOException exception) {
            // logs
        }
    }

    public void run() {
        try {
            Response response;
            do {
                Request deserialized = get(socket);
                response = serverBridge.getServer().getRequest(deserialized);
                send(response, socket);
            } while (response.getPrompt() != Prompt.DISCONNECTED);

            serverBridge.getServer().save();
            socket.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
