package ru.lexender.project.server.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;

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

    public abstract void execute(Invoker invoker) throws CommandExecutionException;
}
