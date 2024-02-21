package ru.lexender.project.client.receiver;

public class StringInput extends Input {
    public StringInput(String string) {
        super(string);
    }

    public String get() {
        return getInputObject().toString();
    }
}
