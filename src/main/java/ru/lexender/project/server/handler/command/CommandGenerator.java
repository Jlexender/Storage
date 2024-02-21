package ru.lexender.project.server.handler.command;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

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

}
