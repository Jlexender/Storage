package ru.lexender.project.server.exception.handler;

import lombok.Getter;

/**
 * An exception that occurs due to builder transformation to Object failure.
 */
@Getter
public class ObjectBuilderException extends Exception {
    String input;

    public ObjectBuilderException(String message, String input) {
        super(message);
        this.input = input;
    }

    public ObjectBuilderException(Exception exception) {
        super(exception.getMessage());
    }

    public ObjectBuilderException(String message) {
        super(message);
    }
}
