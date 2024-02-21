package ru.lexender.project.server.handler;

import ru.lexender.project.server.exception.io.handling.UnknownCommandException;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandGenerator;
import ru.lexender.project.server.handler.command.list.Show;
import ru.lexender.project.server.handler.command.list.*;
import ru.lexender.project.server.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.handler.builder.list.PersonBuilder;

import java.util.List;
import java.util.Map;

public class DefaultHandler implements IHandle {
    private final StorageObjectBuilder builder;

    public DefaultHandler(StorageObjectBuilder builder) {
        this.builder = builder;
    }

    public Command handle(List<String> args) throws UnknownCommandException {
        String command = args.get(0);
        args.remove(0);

        CommandGenerator.generate(
                new Help(),
                new Info(),
                new Show(),
                new Save(),
                new Exit(),
                new Add(builder),
                new Clear(),
                new UpdateId(builder),
                new RemoveById(),
                new AddIfMin(builder),
                new RemoveGreater(builder),
                new FilterStartsWithName(),
                new CountGreaterThanGroupAdmin(new PersonBuilder()),
                new PrintFieldAscendingSemesterEnum()
        );

        try {
            return CommandGenerator.getCommandList().get(command);
        } catch (Exception exception) {
            throw new UnknownCommandException(exception.getMessage(), command);
        }
    }
}
