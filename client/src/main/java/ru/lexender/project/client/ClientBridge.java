package ru.lexender.project.client;

import ru.lexender.project.client.io.Output;
import ru.lexender.project.client.script.ScriptManager;
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
import java.util.List;

/**
 * client-server
 */

public class ClientBridge {
    private final Client client;
    private final String hostname;
    private final int port;
    private final ScriptManager scriptManager;

    public ClientBridge(Client client, String hostname, int port) {
        this.client = client;
        this.hostname = hostname;
        this.port = port;
        this.scriptManager = new ScriptManager(client);
    }

    public void run() {
        try (SocketChannel channel = SocketChannel.open()) {
            channel.connect(new InetSocketAddress(hostname, port));
            Response helloResponse = getResponse(channel.socket());
            client.respondent().respond(client.transcriber().transcribe(helloResponse));

            sendRequest(new Request(new Input(""), client.userdata()), channel);
            Response identification = getResponse(channel.socket());
            if (identification.getPrompt() == Prompt.DISCONNECTED ||
                    identification.getPrompt() == Prompt.AUTHENTICATION_FAILED) {
                client.respondent().respond(client.transcriber().transcribe(identification));
                return;
            }

            Response response = new Response(Prompt.ALL_OK);
            do {
                Request request = client.getRequest(response.getValidator());
                if (scriptManager.isScriptCommand(request)) {
                    processRequests(channel, scriptManager.getBatch(request.getInput()));
                    continue;
                }
                sendRequest(request, channel);

                response = getResponse(channel.socket());
                client.respondent().respond(client.transcriber().transcribe(response));
            } while (response.getPrompt() != Prompt.DISCONNECTED &&
                    response.getPrompt() != Prompt.AUTHENTICATION_FAILED);
        } catch (Exception exception) {
            client.respondent().respond(new Output(exception.getMessage()));
        }
    }

    public void processRequests(SocketChannel channel, List<Request> requests) {
        for (Request request: requests) {
            sendRequest(request, channel);
            Response response = getResponse(channel.socket());
            client.respondent().respond(client.transcriber().transcribe(response));
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