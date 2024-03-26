package ru.lexender.project.client;

import lombok.AllArgsConstructor;
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


    private boolean isServerAvailable() {
        try (Socket socket = new Socket(hostname, port)) {
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    @AllArgsConstructor
    private class TimeoutTask implements Runnable {
        private int iterations, ms;
        private boolean userOutput;
        private Request latestRequest;

        public void run() {
            try {
                if (userOutput)
                    client.respondent().respond(new Output("Checking if server is available..."));
                do {
                    if (isServerAvailable()) {
                        ClientBridge.this.run(latestRequest);
                        return;
                    }
                    Thread.sleep(ms);
                    if (userOutput)
                        client.respondent().respond(
                                new Output(
                                        String.format("Server is unavailable, retrying in %2.2f s...", ms/1000.0)
                                )
                        );
                } while (iterations-- > 0);

                if (userOutput)
                    client.respondent().respond(new Output("Timed out."));
            } catch (InterruptedException exception) {
                client.respondent().respond(new Output(exception.getMessage()));
            }
        }
    }

    private void run(Request latestRequest) {
        try (Socket socket = new Socket(hostname, port)) {
            Response helloResponse = getResponse(socket);
            boolean isNull = latestRequest == null;
            if (isNull) {
                client.respondent().respond(client.transcriber().transcribe(helloResponse));
                latestRequest = new Request(new Input(""), client.userdata());
            }

            sendRequest(latestRequest, socket);
            Response response = getResponse(socket);

            if (!isNull) client.respondent().respond(client.transcriber().transcribe(response));

            if (response.getPrompt() == Prompt.AUTHENTICATION_FAILED) {
                client.respondent().respond(client.transcriber().transcribe(response));
                return;
            }

            Request request;
            do {
                request = client.getRequest(response.getValidator());
                if (scriptManager.isScriptCommand(request)) {
                    processRequests(socket, scriptManager.getBatch(request.getInput()));
                    continue;
                }
                sendRequest(request, socket);
                response = getResponse(socket);
                if (response.getPrompt() == Prompt.CONNECTION_UNAVAILABLE)
                    break;

                client.respondent().respond(client.transcriber().transcribe(response));
            } while (response.getPrompt() != Prompt.CONNECTION_UNAVAILABLE &&
                    response.getPrompt() != Prompt.AUTHENTICATION_FAILED &&
                    response.getPrompt() != Prompt.DISCONNECTED);

            if (response.getPrompt() == Prompt.CONNECTION_UNAVAILABLE) {
                TimeoutTask silentTask = new TimeoutTask(10, 1000, false, request);
                silentTask.run();
                TimeoutTask pingTask = new TimeoutTask(10, 1000, true, request);
                pingTask.run();
            }

        } catch (Exception exception) {
            client.respondent().respond(new Output(exception.getMessage()));
            TimeoutTask pingTask = new TimeoutTask(5, 5000, true, null);
            pingTask.run();
        }
    }


    public void run() {
        run(null);
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
        } catch (IOException ignored) {}
    }

    public Response getResponse(Socket socket) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return (Response) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            return new Response(Prompt.CONNECTION_UNAVAILABLE);
        }
    }


}