package ru.lexender.project.client;

import lombok.Getter;
import ru.lexender.project.client.io.Output;
import ru.lexender.project.inbetween.Input;
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

@Getter
public class ClientBridge {
    private final Client client;
    private final String hostname;
    private final int port;

    public ClientBridge(Client client, String hostname, int port) {
        this.client = client;
        this.hostname = hostname;
        this.port = port;

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
            client.getRespondent().respond(new Output("Unable to send request") {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
        }
    }

    public Response getResponse(Socket socket) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return (Response) objectInputStream.readObject();
        } catch (Exception exception) {
            return null;
        }
    }

    public void run() {
        try (SocketChannel channel = SocketChannel.open()) {
            InetSocketAddress address = new InetSocketAddress(hostname, port);
            channel.connect(address);

            while (true) {
                Response response = getResponse(channel.socket());
                getClient().getRespondent().respond(getClient().getTranscriber().transcribe(response));
                if (response.getPrompt() == Prompt.DISCONNECTED) return;

                Input input = client.getReceiver().receive();
                while (!response.getValidator().test(input.get())) {
                    client.getRespondent().respond(new Output("Invalid argument") {
                        @Override
                        public String get() {
                            return getOutputObject().toString();
                        }
                    });
                    input = client.getReceiver().receive();
                }
                Request request = new Request(input);
                sendRequest(request, channel);
            }
        } catch (IOException exception) {
            getClient().getRespondent().respond(new Output(exception.getMessage()) {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
        }

    }

}