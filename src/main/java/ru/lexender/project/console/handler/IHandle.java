package ru.lexender.project.console.handler;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.exception.console.handler.InputHandleException;

public interface IHandle {
    public Command handle(String rawInput) throws InputHandleException;
}
