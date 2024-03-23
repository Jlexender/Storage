package ru.lexender.project.client;

import ru.lexender.project.client.io.Output;
import ru.lexender.project.client.io.StringInput;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;

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
            sendRequest(new Request(new StringInput(""), client.userdata()), channel);

            Response helloResponse = getResponse(channel.socket());
            client.respondent().respond(client.transcriber().transcribe(helloResponse));

            Response identification = getResponse(channel.socket());
            if (identification.getPrompt() == Prompt.DISCONNECTED ||
                    identification.getPrompt() == Prompt.AUTHENTICATION_FAILED) {
                client.respondent().respond(client.transcriber().transcribe(identification));
                return;
            }

            Response response = new Response(Prompt.ALL_OK);
            do {
                Request request = client.getRequest(response.getValidator());
                sendRequest(request, channel);

                response = getResponse(channel.socket());
                client.respondent().respond(client.transcriber().transcribe(response));
            } while (response.getPrompt() != Prompt.DISCONNECTED &&
                    response.getPrompt() != Prompt.AUTHENTICATION_FAILED);
        } catch (Exception exception) {
            client.respondent().respond(new Output(exception.getMessage()));
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
            client.respondent().respond(new Output("Unable to send request"));
        }
    }

    public Response getResponse(Socket socket) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return (Response) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            return new Response(Prompt.DISCONNECTED);
        }
    }
}