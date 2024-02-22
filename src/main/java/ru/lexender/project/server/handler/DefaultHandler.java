package ru.lexender.project.server.handler;

import ru.lexender.project.server.exception.io.handling.InvalidArgumentsException;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.handler.builder.list.PersonBuilder;
import ru.lexender.project.server.handler.builder.list.StudyGroupBuilder;
import ru.lexender.project.server.handler.command.ArgumentedCommand;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandGenerator;
import ru.lexender.project.server.handler.command.list.Add;
import ru.lexender.project.server.handler.command.list.AddIfMin;
import ru.lexender.project.server.handler.command.list.Clear;
import ru.lexender.project.server.handler.command.list.CountGreaterThanGroupAdmin;
import ru.lexender.project.server.handler.command.list.Exit;
import ru.lexender.project.server.handler.command.list.FilterStartsWithName;
import ru.lexender.project.server.handler.command.list.Help;
import ru.lexender.project.server.handler.command.list.Info;
import ru.lexender.project.server.handler.command.list.PrintFieldAscendingSemesterEnum;
import ru.lexender.project.server.handler.command.list.RemoveById;
import ru.lexender.project.server.handler.command.list.RemoveGreater;
import ru.lexender.project.server.handler.command.list.Save;
import ru.lexender.project.server.handler.command.list.Show;
import ru.lexender.project.server.handler.command.list.UpdateId;

import java.util.List;

public class DefaultHandler implements IHandle {
    private final StorageObjectBuilder builder;

    public DefaultHandler() {
        this.builder = new StudyGroupBuilder();

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
    }

    public Command handle(List<String> args) throws InvalidArgumentsException, InvalidCommandException {
        String word = args.get(0);


        Command command = CommandGenerator.get(word);
        if (command instanceof ArgumentedCommand) return command;

        if (command.getArgumentsAmount() != args.size()-1) {
            throw new InvalidArgumentsException("Invalid arguments amount");
        }
        return command;
    }
}
