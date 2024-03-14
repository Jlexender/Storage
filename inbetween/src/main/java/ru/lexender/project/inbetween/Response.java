package ru.lexender.project.inbetween;

import lombok.Getter;
import lombok.ToString;
import ru.lexender.project.inbetween.validator.Validator;

import java.io.Serializable;

@Getter @ToString
public class Response implements Serializable {
    Prompt prompt;
    String message;
    Validator validator;

    public Response(Prompt prompt, String message, Validator validator) {
        this.prompt = prompt;
        this.message = message;
        this.validator = validator;
    }

    public Response(Prompt prompt, String message) {
        this.prompt = prompt;
        this.message = message;
        this.validator = new Validator();
    }

    public Response(Prompt prompt) {
        this.prompt = prompt;
        this.message = "No message provided";
        this.validator = new Validator();
    }
}