package ru.lexender.project.inbetween;

/**
 * Transferring between server and client.
 */

public interface Bridge {
    public void send(Request request);
    public void send(Response response);
}
