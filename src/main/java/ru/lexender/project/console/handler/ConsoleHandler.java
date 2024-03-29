package ru.lexender.project.console.handler;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.command.CommandGenerator;
import ru.lexender.project.console.command.list.Add;
import ru.lexender.project.console.command.list.AddIfMin;
import ru.lexender.project.console.command.list.Clear;
import ru.lexender.project.console.command.list.CountGreaterThanGroupAdmin;
import ru.lexender.project.console.command.list.ExecuteFileScript;
import ru.lexender.project.console.command.list.Exit;
import ru.lexender.project.console.command.list.FilterStartsWithName;
import ru.lexender.project.console.command.list.Help;
import ru.lexender.project.console.command.list.History;
import ru.lexender.project.console.command.list.Info;
import ru.lexender.project.console.command.list.Init;
import ru.lexender.project.console.command.list.PrintFieldAscendingSemesterEnum;
import ru.lexender.project.console.command.list.RemoveById;
import ru.lexender.project.console.command.list.RemoveGreater;
import ru.lexender.project.console.command.list.Save;
import ru.lexender.project.console.command.list.Show;
import ru.lexender.project.console.command.list.UpdateId;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.console.handler.builder.list.PersonBuilder;
import ru.lexender.project.exception.console.handler.InputHandleException;
import ru.lexender.project.exception.console.handler.UnknownCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class provided for handling Console user input. Implements IHandle.
 * @see ru.lexender.project.console.handler.IHandle
 */
public class ConsoleHandler implements IHandle {
    private final StorageObjectBuilder builder;

    public ConsoleHandler(StorageObjectBuilder builder) {
        this.builder = builder;
    }

    public Command handle(String rawInput) throws InputHandleException {

        String abbreviation = formatToAbbreviation(rawInput);
        List<String> arguments = formatToArguments(rawInput);

        try {

            CommandGenerator.generate(
                    new Help(),
                    new Info(),
                    new Show(),
                    new Save(),
                    new Exit(),
                    new Add(arguments, builder),
                    new Clear(),
                    new UpdateId(arguments, builder),
                    new RemoveById(arguments),
                    new AddIfMin(arguments, builder),
                    new RemoveGreater(arguments, builder),
                    new History(),
                    new ExecuteFileScript(arguments),
                    new FilterStartsWithName(arguments),
                    new CountGreaterThanGroupAdmin(arguments, new PersonBuilder()),
                    new PrintFieldAscendingSemesterEnum(),
                    new Init()
            );

            for (Command command: CommandGenerator.getCommandList().values()) {
                if (command.getAbbreviation().equals(abbreviation)) {
                    return command;
                }
            }

            throw new UnknownCommandException("Unknown command, type \"help\" for command list", abbreviation);
        } catch (Exception exception) {
            throw new InputHandleException(exception);
        }
    }

    private String formatToAbbreviation(String rawInput) {
        Scanner scanner = new Scanner(rawInput);
        return scanner.hasNext() ? scanner.next() : "";
    }

    private List<String> formatToArguments(String rawInput) {
        List<String> arguments = new ArrayList<>();
        Scanner scanner = new Scanner(rawInput);
        if (!scanner.hasNext()) return arguments;
        scanner.next();
        while (scanner.hasNext()) arguments.add(scanner.next());
        return arguments;
    }
}
