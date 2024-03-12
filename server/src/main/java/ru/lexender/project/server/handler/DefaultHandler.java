package ru.lexender.project.server.handler;

import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.handler.command.Command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultHandler implements IHandle {
    private final Set<Command> validCommands;

    public DefaultHandler() {
        validCommands = new HashSet<>();
    }

    public void registerCommands(Command ... commands) {
        validCommands.clear();
        validCommands.addAll(Arrays.asList(commands));
    }

    public Command handle(List<String> args) throws InvalidCommandException {
        String word = args.get(0);

        for (Command command: validCommands) {
            if (command.getAbbreviation().equals(word))
                return command;
        }

        throw new InvalidCommandException("Invalid command", word);
    }
}