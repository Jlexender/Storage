package ru.lexender.project.inbetween;

import lombok.Getter;
import ru.lexender.project.client.Client;
import ru.lexender.project.client.io.Output;
import ru.lexender.project.inbetween.validator.Validator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Optional;

/**
 * client-server
 */

@Getter
public class ClientBridge {
    private final Client client;
    private final String hostname;
    private final int port;

    public ClientBridge(Client client, String hostname, int port) throws IOException {
        this.client = client;
        this.hostname = hostname;
        this.port = port;

    }

    public void send(Request request, SocketChannel channel) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        } catch (IOException exception) {
            client.getRespondent().respond(new Output("Unable to send request") {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
        }
    }

    public Response get(SocketChannel channel) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(channel.socket().getInputStream());
            return (Response) inputStream.readObject();
        } catch (Exception exception) {
            client.getRespondent().respond(new Output("Can't get response") {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
            return null;
        }
    }

    public void run() {
        SocketChannel channel;
        try {
            SocketAddress address = new InetSocketAddress(hostname, port);
            channel = SocketChannel.open(address);
        } catch (IOException exception) {
            String msg = String.format("Can't connect: is server %s:%d really running?", hostname, port);
            client.getRespondent().respond(new Output(msg) {
                @Override
                public String get() {
                    return msg;
                }
            });
            return;
        } catch (IllegalArgumentException exception) {
            String msg = "Port is out of range";
            client.getRespondent().respond(new Output(msg) {
                @Override
                public String get() {
                    return msg;
                }
            });
            return;
        }

        client.getRespondent().respond(new Output(String.format("Connection to %s:%d has been established!\n", hostname, port)) {
            @Override
            public String get() {
                return getOutputObject().toString();
            }
        });

        Optional<Response> handshakeResponse = Optional.ofNullable(get(channel));
        if (handshakeResponse.isPresent())
            client.getRespondent().respond(client.getTranscriber().transcribe(handshakeResponse.get()));


        Response deserialized;
        Validator recentValidator = new Validator();
        do {
            Request query = client.getRequest(recentValidator);
            send(query, channel);
            deserialized = get(channel);
            recentValidator = deserialized.getValidator();
            client.getRespondent().respond(client.getTranscriber().transcribe(deserialized));
        } while (deserialized.getPrompt() != Prompt.DISCONNECTED);
    }

}