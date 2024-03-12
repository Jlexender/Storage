package ru.lexender.project.client.io;

import ru.lexender.project.inbetween.Input;

public class StringInput extends Input {
    public StringInput(String string) {
        super(string);
    }

    public String get() {
        return getInputObject().toString();
    }
}
