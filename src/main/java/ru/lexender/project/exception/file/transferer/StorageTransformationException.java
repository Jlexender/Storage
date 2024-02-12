package ru.lexender.project.exception.file.transferer;

/**
 * An exception that occurs due to data parsing/serialization failure.
 */
public class StorageTransformationException extends Exception {
    public StorageTransformationException(String message) {
        super(message);
    }
}
