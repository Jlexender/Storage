package ru.lexender.project.inbetween;

import ru.lexender.project.client.io.Input;
import ru.lexender.project.server.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Session extends Thread {
    private final Server server;
    private final SocketChannel socketChannel;

    public Session(Server server, SocketChannel socketChannel) {
        this.server = server;
        this.socketChannel = socketChannel;
    }

    public Request getRequest(SocketChannel channel) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(channel.socket().getInputStream());
            return (Request) objectInputStream.readObject();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return new Request(new Input("") {
            @Override
            public String get() {
                return "";
            }
        });
    }

    public void sendResponse(SocketChannel channel, Response response) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void run() {
        Response response;
        do {
            response = server.getRequest(getRequest(socketChannel));
            sendResponse(socketChannel, response);
        } while (response.getPrompt() != Prompt.DISCONNECTED);
    }
}
