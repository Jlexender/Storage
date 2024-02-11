package ru.lexender.project.exception.console.command;

import lombok.Getter;
import ru.lexender.project.console.command.Command;

@Getter
public class CommandExecutionException extends Exception {
    Command command;

    public CommandExecutionException(String message, Command command) {
        super(message);
        this.command = command;
    }

    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(Exception exception) {
        super(exception.getMessage());
    }
}
