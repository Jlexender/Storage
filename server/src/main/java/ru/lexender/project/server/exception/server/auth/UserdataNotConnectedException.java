package ru.lexender.project.server.exception.server.auth;

public class UserdataNotConnectedException extends Exception {
    public UserdataNotConnectedException(String message) {
        super(message);
    }

    public UserdataNotConnectedException(Exception exception) {
        this(exception.getMessage());
    }
}
