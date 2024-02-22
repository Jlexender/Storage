package ru.lexender.project.server.invoker;

import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;

import java.util.List;

public interface CommandInvoker {
    public Response invoke(Command command, List<String> args);
    public List<Command> getHistory();
    public Command peekPreviousCommand();
}
