package ru.lexender.project.server.invoker;

import lombok.Getter;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.ArgumentedCommand;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.file.FileSystem;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Class provided for CommandInvoker interface.
 * NOTE: 1 request - 1 response.
 * Except: Constructor commands: 1 input - 1 response.
 * @see CommandInvoker
 */

@Getter
public class Invoker implements CommandInvoker {
    private final IStore storage;
    private final FileSystem fileSystem;
    private final Stack<Command> history;

    public Invoker(IStore storage, FileSystem fileSystem) {
        this.storage = storage;
        this.fileSystem = fileSystem;

        history = new Stack<>();
    }

    public Response invoke(Command command, List<String> args) {
        history.add(command);
        if (command instanceof ArgumentedCommand)
            return ((ArgumentedCommand) command).invoke(this, args);
        return command.invoke(this);
    }

    public Command peekPreviousCommand() throws EmptyStackException {
       return history.peek();
    }
}
