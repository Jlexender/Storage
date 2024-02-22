package ru.lexender.project.server.handler.command;

import lombok.Getter;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class that generates a Command object with defined arguments.
 * Used for defining user's input as a Command with right arguments and invoking it.
 */
public class CommandGenerator {
    @Getter
    private static final Map<String, Command> commandList = new LinkedHashMap<>();

    public static void generate(Command ... commands) {
        for (Command command : commands) {
            if (commandList.containsKey(command.getAbbreviation()))
                commandList.replace(command.getAbbreviation(), command);
            else commandList.put(command.getAbbreviation(), command);
        }
    }

    public static Command get(String command) throws InvalidCommandException {
        Optional<Command> commandOptional = Optional.ofNullable(commandList.get(command));
        if (commandOptional.isEmpty()) throw new InvalidCommandException("Unknown command", command);
        return commandOptional.get();
    }

}
