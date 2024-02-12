package ru.lexender.project.exception.storage.object;

/**
 * An exception that occurs due to already initialized StorageObject object.
 * @deprecated
 */
public class AlreadyInitializedException extends Exception {

    public AlreadyInitializedException(String message) {
        super(message);
    }
}
