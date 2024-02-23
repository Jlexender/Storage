package ru.lexender.project.inbetween;

import ru.lexender.project.client.Client;
import ru.lexender.project.server.Server;

/**
 * client-server
 */

public class Bridge {
    private final Client client;
    private final Server server;

    public Bridge(Client client, Server server) {
        this.client = client;
        this.server = server;
    }
}
