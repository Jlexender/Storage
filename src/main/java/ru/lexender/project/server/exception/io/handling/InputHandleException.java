package ru.lexender.project.server.exception.io.handling;

import lombok.Getter;

/**
 * An exception that occurs due to Handler input handling failure.
 */
@Getter
public class InputHandleException extends Exception {
    String input;
    public InputHandleException(String message, String input) {
        super(message);
        this.input = input;
    }

    public InputHandleException(Exception exception) {
        super(exception.getMessage());
    }

    public InputHandleException(String message) {
        super(message);
    }
}
