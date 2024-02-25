package ru.lexender.project.inbetween;

import lombok.Getter;
import ru.lexender.project.client.Client;
import ru.lexender.project.client.io.Output;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

/**
 * client-server
 */

@Getter
public class ClientBridge {
    private final Client client;
    private final Socket socket;
    private final String hostname;
    private final int port;
    private final SocketChannel channel;

    public ClientBridge(Client client, String hostname, int port) throws IOException {
        this.client = client;
        this.hostname = hostname;
        this.port = port;

        socket = new Socket(hostname, port);

        SocketAddress address = new InetSocketAddress(hostname, port);
        this.channel = SocketChannel.open(address);
    }

    public void send(Request request) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
        } catch (IOException exception) {
            client.getRespondent().respond(new Output("Unable to send request") {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
        }
    }

    public Response get() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (Response) inputStream.readObject();
        } catch (Exception exception) {
            client.getRespondent().respond(new Output("Can't get response") {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
            return new Response(Prompt.DISCONNECTED);
        }
    }

    public void run() {
        System.out.printf("Connection to %s:%d has been established!\n", hostname, port);
        Response deserialized;
        do {
            send(client.getRequest());
            deserialized = get();
            client.getRespondent().respond(client.getTranscriber().transcribe(deserialized));
        } while (deserialized.getPrompt() != Prompt.DISCONNECTED);
    }

}