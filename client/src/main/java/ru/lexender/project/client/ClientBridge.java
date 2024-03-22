package ru.lexender.project.client;

import lombok.Getter;
import ru.lexender.project.client.io.Output;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.inbetween.validator.Validator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * client-server
 */

public record ClientBridge(Client client, String hostname, int port) {
    public void run() {
        try (SocketChannel channel = SocketChannel.open()) {
            channel.connect(new InetSocketAddress(hostname, port));


            Response response = getResponse(channel.socket());
            client.respondent().respond(client.transcriber().transcribe(response));

            Validator validator = new Validator();
            while (true) {
                Request request = client.getRequest(validator);
                sendRequest(request, channel);
                response = getResponse(channel.socket());
                client.respondent().respond(client.transcriber().transcribe(response));

                validator = response.getValidator();
            }

        } catch (IOException exception) {
            client.respondent().respond(new Output(exception.getMessage()) {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
        }
    }


    public void sendRequest(Request request, SocketChannel channel) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            while (buffer.hasRemaining())
                channel.write(buffer);
        } catch (IOException exception) {
            client.respondent().respond(new Output("Unable to send request") {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
        }
    }

    public synchronized Response getResponse(Socket socket) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return (Response) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException exception) {
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}