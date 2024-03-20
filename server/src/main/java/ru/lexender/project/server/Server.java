package ru.lexender.project.server;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;
import ru.lexender.project.server.handler.DefaultHandler;
import ru.lexender.project.server.handler.builder.list.PersonBuilder;
import ru.lexender.project.server.handler.builder.list.StudyGroupBuilder;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.handler.command.list.Add;
import ru.lexender.project.server.handler.command.list.AddIfMin;
import ru.lexender.project.server.handler.command.list.Clear;
import ru.lexender.project.server.handler.command.list.CountGreaterThanGroupAdmin;
import ru.lexender.project.server.handler.command.list.Exit;
import ru.lexender.project.server.handler.command.list.FilterStartsWithName;
import ru.lexender.project.server.handler.command.list.Help;
import ru.lexender.project.server.handler.command.list.History;
import ru.lexender.project.server.handler.command.list.Info;
import ru.lexender.project.server.handler.command.list.PrintFieldAscendingSemesterEnum;
import ru.lexender.project.server.handler.command.list.RemoveById;
import ru.lexender.project.server.handler.command.list.RemoveGreater;
import ru.lexender.project.server.handler.command.list.Save;
import ru.lexender.project.server.handler.command.list.Show;
import ru.lexender.project.server.handler.command.list.UpdateId;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.io.decoder.DefaultDecoder;
import ru.lexender.project.server.io.decoder.IDecode;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.transfering.ITransfer;

import java.util.LinkedList;
import java.util.List;


@Getter
public class Server {
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    private final IStore storage;
    private final Invoker invoker;
    private final ServerConsole console;

    public Server(IStore storage, Invoker invoker) {
        this.storage = storage;
        this.invoker = invoker;
        this.console = new ServerConsole(this);

        try {
            ITransfer transferer = invoker.getTransferer();
            transferer.transferIn();
            logger.info("Data transfer OK");
        } catch (StorageTransferException exception) {
            logger.warn("Loading storage FAILED: all temporary results may be lost!");
        }
    }

    public Response handle(Request request) {
        Command[] userCommands = {
                new Help(),
                new Info(),
                new Show(),
                new Exit(),
                new History(),
                new Add(new StudyGroupBuilder()),
                new Clear(),
                new UpdateId(new StudyGroupBuilder()),
                new RemoveById(),
                new AddIfMin(new StudyGroupBuilder()),
                new RemoveGreater(new StudyGroupBuilder()),
                new FilterStartsWithName(),
                new CountGreaterThanGroupAdmin(new PersonBuilder()),
                new PrintFieldAscendingSemesterEnum()
        };

        DefaultHandler handler = new DefaultHandler();
        IDecode decoder = new DefaultDecoder();

        handler.registerCommands(userCommands);
        invoker.registerCommands(userCommands);
        try {
            if (!invoker.getHistory().empty()) {
                Command previousCommand = invoker.peekPreviousCommand();
                if (previousCommand.getStatus() == CommandStatus.WAITING_FOR_ARGUMENT) {
                    List<String> newArgs = new LinkedList<>(((ConstructorCommand) previousCommand).getRecentArguments());
                    newArgs.add(request.getRawMessage());

                    logger.info("Constructor command {} relaunched with args {}", previousCommand, newArgs);
                    return invoker.invoke(previousCommand, newArgs);
                }
            }

            try {
                Command command = handler.handle(decoder.decode(request));
                if (command.equals(new Save()))
                    throw new InvalidCommandException();
                logger.info("Command handled as {}", command);
                return invoker.invoke(command, decoder.getArguments(request));
            } catch (InvalidCommandException exception) {
                logger.warn("Command identified as invalid");
                return new Response(Prompt.INVALID_COMMAND);
            }
        } catch (CommandExecutionException exception) {
            return new Response(exception.getResponse().getPrompt());
        }

    }

}

