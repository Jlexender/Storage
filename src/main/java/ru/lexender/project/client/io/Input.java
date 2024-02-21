package ru.lexender.project.client.io;

import lombok.Getter;

@Getter
public abstract class Input {
    private final Object inputObject;

    public Input(Object inputObject) {
        this.inputObject = inputObject;
    }

    public abstract String get();
}
