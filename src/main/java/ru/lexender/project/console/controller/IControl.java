package ru.lexender.project.console.controller;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.exception.console.command.CommandExecutionException;

/**
 * Controls command execution process.
 */
public interface IControl {
    public void execute(Command command) throws CommandExecutionException;
}
