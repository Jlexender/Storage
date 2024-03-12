package ru.lexender.project.server.exception.command;

import lombok.Getter;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;

/**
 * An exception that occurs due to command execution failure.
 * Provides Response object.
 */
@Getter
public class CommandExecutionException extends Exception {
    private final Command command;
    private final Response response;

    public CommandExecutionException(String message, Command command, Response response) {
        super(message);
        this.command = command;
        this.response = response;
    }

}
