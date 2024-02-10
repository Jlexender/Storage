package ru.lexender.project.exception.console.handler;

import lombok.Getter;

@Getter
public class UnknownCommandException extends InputHandleException {
    private final String command;

    public UnknownCommandException(String message, String command) {
        super(message);
        this.command = command;
    }
}
