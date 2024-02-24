package ru.lexender.project.inbetween;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Response implements Serializable {
    Prompt prompt;
    String message;

    public Response(Prompt prompt, String message) {
        this.prompt = prompt;
        this.message = message;
    }

    public Response(Prompt prompt) {
        this.prompt = prompt;
        this.message = "";
    }
}

// TBD