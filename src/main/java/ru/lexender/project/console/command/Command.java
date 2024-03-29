package ru.lexender.project.console.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.command.CommandExecutionException;

/**
 * Basic abstract class for command. Used for static commands.
 */

@Getter
@EqualsAndHashCode
public abstract class Command {
    private final String info;
    private final String abbreviation;

    public Command(@NonNull String abbreviation, @NonNull String info) {
        this.abbreviation = abbreviation;
        this.info = info;
    }

    public abstract void execute(Controller controller) throws CommandExecutionException;
}
