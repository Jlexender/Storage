package ru.lexender.project.console.handler;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.exception.console.handler.InputHandleException;

/**
 * Handles user input, transforms it into a Command object.
 */
public interface IHandle {
    public Command handle(String rawInput) throws InputHandleException;
}
