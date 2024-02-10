package ru.lexender.project.exception.file.transferer;

public class StorageTransferException extends Exception {
    public StorageTransferException(String message) {
        super(message);
    }

    public StorageTransferException(Exception exception) {
        this(exception.getMessage());
    }
}
