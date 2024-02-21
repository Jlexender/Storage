package ru.lexender.project.client.handler;

import ru.lexender.project.client.handler.builder.ObjectBuilder;

/**
 * Input -> Request
 */
public class Handler {
    private final ObjectBuilder builder;
    public Handler(ObjectBuilder builder) {
        this.builder = builder;
    }
}
