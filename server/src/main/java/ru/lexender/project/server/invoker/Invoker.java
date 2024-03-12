package ru.lexender.project.server.invoker;

import lombok.Getter;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.handler.command.ArgumentedCommand;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.file.FileSystem;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

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
    private final Set<Command> allowedCommands;

    public Invoker(IStore storage, FileSystem fileSystem) {
        this.storage = storage;
        this.fileSystem = fileSystem;
        history = new Stack<>();
        allowedCommands = new TreeSet<>();
    }

    public Response invoke(Command command, List<String> args) throws CommandExecutionException {
        if (!allowedCommands.contains(command))
            throw new CommandExecutionException("Access denied", command, new Response(Prompt.INVALID_COMMAND));

        history.add(command);
        if (command instanceof ArgumentedCommand)
            return ((ArgumentedCommand) command).invoke(this, args);
        return command.invoke(this);
    }

    public void registerCommands(Command ... commands) {
        allowedCommands.clear();
        allowedCommands.addAll(Arrays.asList(commands));
    }

    public Command peekPreviousCommand() throws EmptyStackException {
       return history.peek();
    }
}