package ru.lexender.project.server;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.io.handling.InvalidArgumentsException;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;
import ru.lexender.project.server.handler.DefaultHandler;
import ru.lexender.project.server.handler.IHandle;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.handler.command.list.Save;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.io.decoder.DefaultDecoder;
import ru.lexender.project.server.io.decoder.IDecode;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.file.transferer.DefaultTransferer;
import ru.lexender.project.server.storage.file.transferer.ITransfer;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;


@Getter
public class Server {
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    private final IStore storage;
    private final Invoker invoker;
    private final AdminConsole console;

    public Server(IStore storage, Invoker invoker) {
        this.storage = storage;
        this.invoker = invoker;
        this.console = new AdminConsole(this);

        Command init = new Command("init", "Transfers storage into collection.") {
            public Response invoke(Invoker invoker) {
                try {
                    ITransfer transferer = new DefaultTransferer(invoker.getFileSystem(), storage);
                    transferer.transferIn();
                    logger.info("Data transfer OK");

                    setStatus(CommandStatus.SUCCESS);
                    return new Response(Prompt.ALL_OK);
                } catch (StorageTransferException exception) {
                    logger.warn("Can't parse file storage");

                    setStatus(CommandStatus.FAIL);
                    return new Response(Prompt.STORAGE_FILE_UNAVAILABLE, "Can't parse");
                }
            }
        };

        invoker.invoke(init, null);
    }

    public Response getRequest(Request request) {
        try {
            Command previousCommand = invoker.peekPreviousCommand();
            if (previousCommand.getStatus() == CommandStatus.WAITING_FOR_ARGUMENT) {
                List<String> newArgs = new LinkedList<>(((ConstructorCommand) previousCommand).getRecentArguments());
                newArgs.add(request.getRawMessage());
                return invoker.invoke(previousCommand, newArgs);
            }
        } catch (EmptyStackException exception) {
            logger.warn("Can't restore peeked command");
        }

        IDecode decoder = new DefaultDecoder();
        IHandle handler = new DefaultHandler();
        try {
            Command command = handler.handle(decoder.decode(request));
            if (command.equals(new Save()))
                throw new InvalidCommandException();
            logger.info("Command handled as {}", command);
            return invoker.invoke(command, decoder.getArguments(request));
        } catch (InvalidCommandException exception) {
            logger.warn("Command identified as invalid");
            return new Response(Prompt.INVALID_COMMAND);
        } catch (InvalidArgumentsException exception) {
            logger.warn("Command arguments are not valid");
            return new Response(Prompt.INVALID_AMOUNT);
        }
    }

}

