package ru.lexender.project.server.invoker;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.exception.command.CommandExecutionException;

/**
 * Controls command execution process.
 */
public interface IExecute {
    public void execute(Command command) throws CommandExecutionException;
}
