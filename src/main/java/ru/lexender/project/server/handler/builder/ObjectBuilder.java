package ru.lexender.project.server.handler.builder;

import lombok.AccessLevel;
import lombok.Getter;
import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.invoker.Invoker;

import java.util.List;

/**
 * Tries to initialize an instance of some class by provided arguments (in java.lang.String form)
 * @see ru.lexender.project.server.storage.object.StorageObject
 */

@Getter
public abstract class ObjectBuilder {
    private final int firstArgumentsAmount;
    private final List<String> fieldNames;

    public ObjectBuilder(int firstArgumentsAmount, List<String> fieldNames) {
        this.firstArgumentsAmount = firstArgumentsAmount;
        this.fieldNames = fieldNames;
    }

    public boolean isValid(List<String> arguments) {
        return arguments.size() == fieldNames.size();
    }

    public abstract boolean validateArgument(String argument, int pointer);

    public abstract Object[] suggest(int pointer);

    public abstract Object build(List<String> arguments) throws IllegalAccessException, BuildFailedException;
}
