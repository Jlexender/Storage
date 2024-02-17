package ru.lexender.project.server.exception.file.transferer;

/**
 * An exception that occurs due to data parsing/serialization failure.
 */
public class StorageTransformationException extends TransformationException {
    public StorageTransformationException(String message) {
        super(message);
    }
}
