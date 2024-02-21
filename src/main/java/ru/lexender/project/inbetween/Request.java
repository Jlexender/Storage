package ru.lexender.project.inbetween;

import lombok.Getter;
import ru.lexender.project.client.io.Input;

@Getter
public class Request {
    private final String message;

    public Request(Input input) {
        this.message = input.get();
    }
}

// TBD
