package ru.lexender.project.server.io.decoder;

import ru.lexender.project.server.io.decoder.builder.ObjectBuilder;

/**
 * Input -> Request
 */
public class Handler {
    private final ObjectBuilder builder;
    public Handler(ObjectBuilder builder) {
        this.builder = builder;
    }
}
