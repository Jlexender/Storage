package ru.lexender.project.server.io;

public class StringInput extends Input {
    public StringInput(String string) {
        super(string);
    }

    public String get() {
        return getInputObject().toString();
    }
}
