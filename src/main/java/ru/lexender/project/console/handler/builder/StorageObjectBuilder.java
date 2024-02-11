package ru.lexender.project.console.handler.builder;

import lombok.Getter;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.handler.StorageObjectBuilderException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

@Getter
public abstract class StorageObjectBuilder {
    private final int firstArgumentsAmount;
    private final List<String> fieldNames;

    public StorageObjectBuilder(int firstArgumentsAmount, List<String > fieldNames) {
        this.firstArgumentsAmount = firstArgumentsAmount;
        this.fieldNames = fieldNames;
    }

    public abstract StorageObject build(List<String> arguments, Controller controller) throws StorageObjectBuilderException;
}
