package ru.lexender.project.inbetween;

import ru.lexender.project.client.exception.handler.InputHandleException;
import ru.lexender.project.server.command.Command;

/**
 * Transferring between server and client.
 */

public interface Bridge {
    public void send(Request request);
    public void send(Response response);
}
