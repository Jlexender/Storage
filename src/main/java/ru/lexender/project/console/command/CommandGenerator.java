package ru.lexender.project.console.command;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

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
