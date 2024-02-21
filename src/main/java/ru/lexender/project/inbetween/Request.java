package ru.lexender.project.inbetween;

import lombok.Getter;
import ru.lexender.project.client.io.Input;

@Getter
public class Request {
    private final String rawMessage;

    public Request(Input input) {
        this.rawMessage = input.get();
    }
}

