package ru.lexender.project.client.io;

import lombok.Getter;

@Getter
public abstract class Output {
    private final Object outputObject;

    public Output(Object outputObject) {
        this.outputObject = outputObject;
    }

    public abstract String get();
}
