package ru.lexender.project.exception.console.handler;

import lombok.Getter;

@Getter
public class InputHandleException extends Exception {
    String input;
    public InputHandleException(String message, String input) {
        super(message);
        this.input = input;
    }

    public InputHandleException(Exception exception) {
        super(exception.getMessage());
    }

    public InputHandleException(String message) {
        super(message);
    }
}
