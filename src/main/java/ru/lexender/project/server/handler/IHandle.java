package ru.lexender.project.server.handler;

import ru.lexender.project.server.exception.io.handling.InvalidArgumentsException;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.handler.command.Command;

import java.util.List;

public interface IHandle {
    public Command handle(List<String> args) throws InvalidCommandException, InvalidArgumentsException;
}
