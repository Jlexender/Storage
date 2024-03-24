package ru.lexender.project.inbetween;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Input implements Serializable {
    private final Object inputObject;

    public Input(Object inputObject) {
        this.inputObject = inputObject;
    }

    public String get() {
        return inputObject.toString();
    }
}
