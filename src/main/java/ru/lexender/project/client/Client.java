package ru.lexender.project.client;

import lombok.Getter;
import ru.lexender.project.client.io.respondent.IRespond;


@Getter
public class Client {
    private final String hostname;
    private final int port;
    private final IRespond respondent;

    public Client(IRespond respondent, String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.respondent = respondent;
    }
}
