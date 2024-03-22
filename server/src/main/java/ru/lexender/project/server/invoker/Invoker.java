package ru.lexender.project.server.invoker;

import lombok.Getter;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.handler.command.ArgumentedCommand;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.transfering.ITransfer;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final ITransfer transferer;
    private final Map<String, Stack<Command>> history;
    private final Set<Command> allowedCommands;
    private String currentUser;

    public Invoker(IStore storage, ITransfer transferer) {
        this.storage = storage;
        this.transferer = transferer;
        history = new HashMap<>();
        allowedCommands = new TreeSet<>();
    }

    public Response invoke(Command command, List<String> args, String username) throws CommandExecutionException {
        if (!allowedCommands.contains(command)) {
            Server.logger.warn("{} tried to use {}", username, command);
            throw new CommandExecutionException("Access denied", command, new Response(Prompt.INVALID_COMMAND));
        }

        Optional<Stack<Command>> history = Optional.ofNullable(this.history.get(username));

        if (history.isPresent()) {
            history.get().add(command);
        } else {
            this.history.put(username, new Stack<>());
            this.history.get(username).add(command);
        }

        currentUser = username;

        if (command instanceof ArgumentedCommand)
            return ((ArgumentedCommand) command).invoke(this, args);
        return command.invoke(this);
    }

    public void registerCommands(Command ... commands) {
        allowedCommands.clear();
        allowedCommands.addAll(Arrays.asList(commands));
    }

    public Command peekPreviousCommand(String username) throws EmptyStackException {
       return history.get(username).peek();
    }

    public Stack<Command> getHistory(String username) {
        return history.get(username);
    }
}
