package ru.lexender.project.exception.file.transferer;

/**
 * An exception that occurs due to failure in connection initialization.
 */
public class StorageIOException extends Exception {
    public StorageIOException(String message) {
        super(message);
    }

    public StorageIOException(Exception exception) {
        this(exception.getMessage());
    }
}
