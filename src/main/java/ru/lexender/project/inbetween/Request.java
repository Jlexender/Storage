package ru.lexender.project.inbetween;

import lombok.Getter;
import lombok.ToString;
import ru.lexender.project.client.io.Input;

import java.io.Serializable;

@Getter @ToString
public class Request implements Serializable {
    private final String rawMessage;

    public Request(Input input) {
        this.rawMessage = input.get();
    }
}

