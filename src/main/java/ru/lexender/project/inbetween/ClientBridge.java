package ru.lexender.project.inbetween;

import ru.lexender.project.client.Client;
import ru.lexender.project.client.io.Output;

import java.io.IOException;
import java.net.Socket;

/**
 * client-server
 */

public class ClientBridge extends Thread {
    private final Client client;
    private final String hostname;
    private final int port;

    public ClientBridge(Client client, String hostname, int port) {
        this.client = client;
        this.hostname = hostname;
        this.port = port;
    }

    public void send(Request request) {

    }

    public void run() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.printf("Connection to %s:%d has been established!\n", hostname, port);
            // Client logic
            socket.close();
        } catch (IOException e) {
            client.getRespondent().respond(new Output("Can't establish connection: is server really running?") {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
        }
    }

}
