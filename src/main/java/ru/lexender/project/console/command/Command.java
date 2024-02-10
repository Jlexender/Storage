package ru.lexender.project.console.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.command.CommandExecutionException;

@Getter
@EqualsAndHashCode
public abstract class Command {
    private final String info;
    private final String abbreviation;
    private final int argumentsAmount;
    protected String syntax;

    public Command(@NonNull String abbreviation, @NonNull String info) {
        this.abbreviation = abbreviation;
        this.info = info;
        syntax = "";
        argumentsAmount = 0;
    }

    public abstract void execute(Controller controller) throws CommandExecutionException;

}
