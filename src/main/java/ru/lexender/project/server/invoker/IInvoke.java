package ru.lexender.project.server.invoker;

import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.exception.command.CommandExecutionException;

/**
 * Controls command execution process.
 */
public interface IInvoke {
    public Response invoke(Command command) throws CommandExecutionException;
}
