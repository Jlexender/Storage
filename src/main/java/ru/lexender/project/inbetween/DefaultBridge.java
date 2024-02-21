package ru.lexender.project.inbetween;

import ru.lexender.project.client.Client;
import ru.lexender.project.server.Server;

/**
 * client-server
 */

public class DefaultBridge implements Bridge {
    private final Client client;
    private final Server server;

    public DefaultBridge(Client client, Server server) {
        this.client = client;
        this.server = server;
    }


    @Override
    public void send(Request request) {
        server.getRequest(request);
    }

    @Override
    public void send(Response response) {
        client.getResponse(response);
    }
}
