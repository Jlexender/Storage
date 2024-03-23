package ru.lexender.project.server.invoker;

import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.handler.command.Command;

import java.util.List;
import java.util.Stack;

public interface CommandInvoker {
    public Response invoke(Command command, List<String> args, String username) throws CommandExecutionException;
    public Stack<Command> getHistory(String username);
    public Command peekPreviousCommand(String username);
    public void registerCommands(List<Command> commands);

}
