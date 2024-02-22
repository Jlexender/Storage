package ru.lexender.project.server.exception.io.handling;

import lombok.Getter;

/**
 * An exception that occurs due to command recognizing failure.
 */
@Getter
public class InvalidCommandException extends Exception {
    private final String command;

    public InvalidCommandException(String message, String command) {
        super(message);
        this.command = command;
    }
}
