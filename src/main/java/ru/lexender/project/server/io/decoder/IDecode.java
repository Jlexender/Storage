package ru.lexender.project.server.io.decoder;

import ru.lexender.project.inbetween.Request;

import java.util.List;

public interface IDecode {
    public List<String> decode(Request request);
    public List<String> getArguments(Request request);
}
