package ru.lexender.project.console.handler;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.command.CommandGenerator;
import ru.lexender.project.console.command.list.Add;
import ru.lexender.project.console.command.list.Exit;
import ru.lexender.project.console.command.list.Help;
import ru.lexender.project.console.command.list.Info;
import ru.lexender.project.console.command.list.Save;
import ru.lexender.project.console.command.list.Show;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.handler.InputHandleException;
import ru.lexender.project.exception.console.handler.UnknownCommandException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleHandler implements IHandle {
    private final StorageObjectBuilder builder;

    public ConsoleHandler(StorageObjectBuilder builder) {
        this.builder = builder;
    }

    public Command handle(String rawInput) throws InputHandleException {

        String abbreviation = formatToAbbreviation(rawInput);
        List<String> arguments = formatToArguments(rawInput);

        try {
            StorageObject definedObject = builder.build(arguments);

            CommandGenerator.generate(
                    new Help(),
                    new Info(),
                    new Show(),
                    new Save(),
                    new Exit(),
                    new Add(definedObject, builder)
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
