package ru.lexender.project.inbetween;

import lombok.Getter;
import ru.lexender.project.client.io.Input;

import java.io.Serializable;

@Getter
public class Request implements Serializable {
    private final String rawMessage;

    public Request(Input input) {
        this.rawMessage = input.get();
    }
}

