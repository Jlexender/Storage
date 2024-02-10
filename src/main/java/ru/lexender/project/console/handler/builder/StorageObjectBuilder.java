package ru.lexender.project.console.handler.builder;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.exception.console.handler.StorageObjectBuilderException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

@Getter
public abstract class StorageObjectBuilder {
    private final int argumentsAmount;
    private final String[] orderedFields;

    public StorageObjectBuilder(int argumentsAmount, @NonNull String[] orderedFields) {
        this.argumentsAmount = argumentsAmount;
        this.orderedFields = orderedFields;
    }

    public abstract StorageObject build(List<String> arguments) throws StorageObjectBuilderException;
}
