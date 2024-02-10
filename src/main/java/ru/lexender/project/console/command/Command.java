package ru.lexender.project.console.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.storage.object.StorageObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

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
