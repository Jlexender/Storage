package ru.lexender.project.server;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.auth.AuthWorker;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.exception.server.auth.UserdataNotConnectedException;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;
import ru.lexender.project.server.handler.DefaultHandler;
import ru.lexender.project.server.handler.command.AccessLevel;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.io.decoder.DefaultDecoder;
import ru.lexender.project.server.io.decoder.IDecode;
import ru.lexender.project.server.storage.transfering.ITransfer;

import java.util.LinkedList;
import java.util.List;


@Getter
public class Server {
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    private final Invoker invoker;
    private final AuthWorker authWorker;

    public Server(Invoker invoker, AuthWorker authWorker) {
        this.invoker = invoker;
        this.authWorker = authWorker;

        try {
            ITransfer transferer = invoker.getTransferer();
            transferer.transferIn();
            logger.info("Data transfer OK");
        } catch (StorageTransferException exception) {
            logger.warn("Loading storage FAILED: all results will be lost after restart!");
        }

        if (!authWorker.getUserdataBridge().isConnectable())
            logger.error("Server UserDB init FAILED: authentication won't work!");
        else logger.info("Userdata connection OK");
    }

    public Response handle(Request request, String username) {
        try {
            if (!authWorker.isValid(request.getUserdata()))
                return new Response(Prompt.AUTHENTICATION_FAILED, "The password that you've entered is invalid.");
        } catch (UserdataNotConnectedException exception) {
            return new Response(Prompt.AUTHENTICATION_FAILED, "UserDB init FAILED");
        }

        DefaultHandler handler = new DefaultHandler();
        IDecode decoder = new DefaultDecoder();

        handler.registerCommands(AccessLevel.ALL);
        invoker.registerCommands(AccessLevel.GUEST);
        try {
            if (invoker.getHistory(username) != null) {
                Command previousCommand = invoker.peekPreviousCommand(username);
                if (previousCommand.getStatus() == CommandStatus.WAITING_FOR_ARGUMENT) {
                    List<String> newArgs = new LinkedList<>(((ConstructorCommand) previousCommand).getRecentArguments());
                    newArgs.add(request.getRawMessage());

                    logger.info("Constructor command {} relaunched with args {}", previousCommand, newArgs);
                    return invoker.invoke(previousCommand, newArgs, username);
                }
            }

            Command command = handler.handle(decoder.decode(request).get(0));
            return invoker.invoke(command, decoder.getArguments(request), username);
        } catch (CommandExecutionException exception) {
            return new Response(exception.getResponse().getPrompt());
        } catch (InvalidCommandException exception) {
            return new Response(Prompt.INVALID_COMMAND);
        }

    }


}

