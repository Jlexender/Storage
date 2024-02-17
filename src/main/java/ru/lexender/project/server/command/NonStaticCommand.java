package ru.lexender.project.server.command;

import lombok.Getter;
import lombok.NonNull;

/**
 * Interactive command, extension of Command abstract class.
 * Used for non-static commands (when the result depends on user's arguments).
 */
@Getter
public abstract class NonStaticCommand extends Command {
    private final int argumentsAmount;

    public NonStaticCommand(@NonNull String abbreviation,
                          @NonNull String info,
                          int argumentsAmount) {
        super(abbreviation, info);
        this.argumentsAmount = argumentsAmount;
    }
}