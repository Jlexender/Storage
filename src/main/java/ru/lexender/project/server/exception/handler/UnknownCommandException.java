package ru.lexender.project.server.exception.handler;

import lombok.Getter;

/**
 * An exception that occurs due to command recognizing failure.
 */
@Getter
public class UnknownCommandException extends InputHandleException {
    private final String command;

    public UnknownCommandException(String message, String command) {
        super(message);
        this.command = command;
    }
}
