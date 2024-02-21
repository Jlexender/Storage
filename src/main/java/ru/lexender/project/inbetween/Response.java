package ru.lexender.project.inbetween;

import lombok.Getter;

@Getter
public class Response {
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