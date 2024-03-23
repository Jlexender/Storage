package ru.lexender.project.server.handler;

import ru.lexender.project.server.Server;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.handler.command.Command;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultHandler implements IHandle {
    private final Set<Command> validCommands;

    public DefaultHandler() {
        validCommands = new HashSet<>();
    }

    public void registerCommands(List<Command> commands) {
        validCommands.addAll(commands);
    }

    public Command handle(List<String> args) throws InvalidCommandException {
        String word = args.get(0);

        for (Command command: validCommands) {
            if (command.getAbbreviation().equals(word)) {
                Server.logger.info("Command handled as {}", command);
                return command;
            }
        }

        Server.logger.info("Command handled as invalid");
        throw new InvalidCommandException("Invalid command", word);
    }
}
