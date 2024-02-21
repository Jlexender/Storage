package ru.lexender.project.server.io;

import lombok.Getter;

@Getter
public abstract class Input {
    private final Object inputObject;

    public Input(Object inputObject) {
        this.inputObject = inputObject;
    }

    public abstract String get();
}
