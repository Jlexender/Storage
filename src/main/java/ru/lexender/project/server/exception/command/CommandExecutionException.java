package ru.lexender.project.server.exception.command;

import lombok.Getter;
import ru.lexender.project.server.command.Command;

/**
 * An exception that occurs due to command execution failure.
 */
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
