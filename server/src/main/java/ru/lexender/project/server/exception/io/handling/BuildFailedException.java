package ru.lexender.project.server.exception.io.handling;

import lombok.Getter;

/**
 * An exception that occurs due to builder transformation to Object failure.
 */
@Getter
public class BuildFailedException extends Exception {
    String input;

    public BuildFailedException(String message, String input) {
        super(message);
        this.input = input;
    }

    public BuildFailedException(Exception exception) {
        super(exception.getMessage());
    }

    public BuildFailedException(String message) {
        super(message);
    }
}
