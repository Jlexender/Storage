package ru.lexender.project.console.command;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.console.handler.builder.IBuild;

/**
 * Interactive command, extension of Command abstract class.
 * Used for interactive commands, that require in-time input capturing.
 */
@Getter
public abstract class InteractiveCommand extends Command {
    private final IBuild objectBuilder;
    private final int argumentsAmount;

    public InteractiveCommand(@NonNull String abbreviation,
                              @NonNull String info,
                              @NonNull IBuild objectBuilder,
                              int argumentsAmount) {
        super(abbreviation, info);
        this.objectBuilder = objectBuilder;
        this.argumentsAmount = argumentsAmount;
    }
}
