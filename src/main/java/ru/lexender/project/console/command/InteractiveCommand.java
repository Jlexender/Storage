package ru.lexender.project.console.command;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;

@Getter
public abstract class InteractiveCommand extends Command {
    private final StorageObjectBuilder objectBuilder;
    private final int argumentsAmount;

    public InteractiveCommand(@NonNull String abbreviation,
                              @NonNull String info,
                              @NonNull StorageObjectBuilder objectBuilder,
                              int argumentsAmount) {
        super(abbreviation, info);
        this.objectBuilder = objectBuilder;
        this.argumentsAmount = argumentsAmount;
    }
}
