package ru.lexender.project.console.command;

import lombok.Getter;
import lombok.NonNull;

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