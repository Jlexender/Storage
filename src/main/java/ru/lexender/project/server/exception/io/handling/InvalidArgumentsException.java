package ru.lexender.project.server.exception.io.handling;

import lombok.Getter;

/**
 * An exception that occurs due to Handler input handling failure.
 */
@Getter
public class InvalidArgumentsException extends Exception {
    String input;
    public InvalidArgumentsException(String message, String input) {
        super(message);
        this.input = input;
    }

    public InvalidArgumentsException(Exception exception) {
        super(exception.getMessage());
    }

    public InvalidArgumentsException(String message) {
        super(message);
    }
}
