package ru.lexender.project.exception.console.handler;

import lombok.Getter;

/**
 * An exception that occurs due to builder transformation to StorageObject failure.
 */
@Getter
public class StorageObjectBuilderException extends Exception {
    String input;

    public StorageObjectBuilderException(String message, String input) {
        super(message);
        this.input = input;
    }

    public StorageObjectBuilderException(Exception exception) {
        super(exception.getMessage());
    }

    public StorageObjectBuilderException(String message) {
        super(message);
    }
}
