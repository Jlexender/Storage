package ru.lexender.project.console.command;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;

@Getter
public abstract class NonStaticCommand extends Command {
    private final StorageObjectBuilder objectBuilder;

    public NonStaticCommand(@NonNull String abbreviation, @NonNull String info, @NonNull StorageObjectBuilder objectBuilder) {
        super(abbreviation, info);
        this.objectBuilder = objectBuilder;
    }
}
