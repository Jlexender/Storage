package ru.lexender.project.server.io.decoder;

import ru.lexender.project.inbetween.Request;
import ru.lexender.project.server.handler.command.Command;

import java.util.List;

public interface IDecode {
    public List<String> decode(Request request);
}
