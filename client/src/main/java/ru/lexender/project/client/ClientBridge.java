package ru.lexender.project.client;

import ru.lexender.project.client.io.Output;
import ru.lexender.project.client.script.ScriptManager;
import ru.lexender.project.inbetween.Input;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected!");

            Response helloResponse = getResponse(socket);
            client.respondent().respond(client.transcriber().transcribe(helloResponse));

            sendRequest(new Request(new Input(""), client.userdata()), socket);
            Response response = getResponse(socket);

            if (response.getPrompt() == Prompt.DISCONNECTED ||
                    response.getPrompt() == Prompt.AUTHENTICATION_FAILED) {
                client.respondent().respond(client.transcriber().transcribe(response));
                return;
            }

            do {
                Request request = client.getRequest(response.getValidator());
                if (scriptManager.isScriptCommand(request)) {
                    processRequests(socket, scriptManager.getBatch(request.getInput()));
                    continue;
                }
                sendRequest(request, socket);

                response = getResponse(socket);
                client.respondent().respond(client.transcriber().transcribe(response));
            } while (response.getPrompt() != Prompt.DISCONNECTED &&
                    response.getPrompt() != Prompt.AUTHENTICATION_FAILED);
        } catch (Exception exception) {
            client.respondent().respond(new Output(exception.getMessage()));
        }
    }

    public void processRequests(Socket socket, List<Request> requests) {
        for (Request request: requests) {
            sendRequest(request, socket);
            Response response = getResponse(socket);
            client.respondent().respond(client.transcriber().transcribe(response));
        }
    }


    public void sendRequest(Request request, Socket socket) {
        try {
            ObjectOutputStream objectOutputStream  = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
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