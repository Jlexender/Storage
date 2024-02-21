package ru.lexender.project.server.sender;

/**
 * Sends a response to the bridge.
 */
public interface ISend {
    public void send(Object message);
}