package ru.lexender.project.client.io;

import lombok.Getter;

@Getter
public class Output {
    private final Object outputObject;

    public Output(Object outputObject) {
        this.outputObject = outputObject;
    }

    public String get() {
        return outputObject.toString();
    }
}
