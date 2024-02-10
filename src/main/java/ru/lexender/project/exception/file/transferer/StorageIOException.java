package ru.lexender.project.exception.file.transferer;

public class StorageIOException extends Exception {
    public StorageIOException(String message) {
        super(message);
    }

    public StorageIOException(Exception exception) {
        this(exception.getMessage());
    }
}
