package ru.lexender.project.server.exception.file.transferer;

/**
 * An exception that occurs due to data transferring failure.
 */
public class StorageTransferException extends Exception {
    public StorageTransferException(String message) {
        super(message);
    }

    public StorageTransferException(Exception exception) {
        this(exception.getMessage());
    }
}
