package ru.lexender.project.console.handler.builder;

import lombok.Getter;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.handler.ObjectBuilderException;

import java.util.List;

/**
 * Tries to initialize an instance of some class by provided arguments (in java.lang.String form)
 * @see ru.lexender.project.storage.object.StorageObject
 */
@Getter
public abstract class ObjectBuilder implements IBuild {
    private final int firstArgumentsAmount;
    private final List<String> fieldNames;

    public ObjectBuilder(int firstArgumentsAmount, List<String > fieldNames) {
        this.firstArgumentsAmount = firstArgumentsAmount;
        this.fieldNames = fieldNames;
    }

    public abstract Object build(List<String> arguments, Controller controller) throws ObjectBuilderException;
    public abstract Object buildInLine(List<String> arguments, Controller controller) throws ObjectBuilderException;
}
