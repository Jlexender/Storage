package ru.lexender.project.server;

import lombok.Getter;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.io.handling.InvalidArgumentsException;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.handler.DefaultHandler;
import ru.lexender.project.server.handler.IHandle;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.io.decoder.DefaultDecoder;
import ru.lexender.project.server.io.decoder.IDecode;
import ru.lexender.project.server.storage.IStore;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;


@Getter
public class Server {
    private final IStore storage;
    private final Invoker invoker;

    public Server(IStore storage, Invoker invoker) {
        this.storage = storage;
        this.invoker = invoker;

    }

    public Response getRequest(Request request) {
        try {
            Command previousCommand = invoker.peekPreviousCommand();
            if (previousCommand.getStatus() == CommandStatus.WAITING_FOR_ARGUMENT) {
                List<String> newArgs = new LinkedList<>(((ConstructorCommand) previousCommand).getRecentArguments());
                newArgs.add(request.getRawMessage());
                return invoker.invoke(previousCommand, newArgs);
            }
        } catch (EmptyStackException ignored) {}

        IDecode decoder = new DefaultDecoder();
        IHandle handler = new DefaultHandler();
        try {
            Command command = handler.handle(decoder.decode(request));
            return invoker.invoke(command, decoder.getArguments(request));
        } catch (InvalidCommandException exception) {
            return new Response(Prompt.INVALID_COMMAND);
        } catch (InvalidArgumentsException exception) {
            return new Response(Prompt.INVALID_AMOUNT);
        }
    }

}

