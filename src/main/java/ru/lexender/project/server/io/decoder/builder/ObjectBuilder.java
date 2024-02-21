package ru.lexender.project.server.io.decoder.builder;

import lombok.Getter;
import ru.lexender.project.server.exception.handler.ObjectBuilderException;
import ru.lexender.project.server.invoker.Invoker;

import java.util.List;

/**
 * Tries to initialize an instance of some class by provided arguments (in java.lang.String form)
 * @see ru.lexender.project.server.storage.object.StorageObject
 */
@Getter
public abstract class ObjectBuilder implements IBuild {
    private final int firstArgumentsAmount;
    private final List<String> fieldNames;

    public ObjectBuilder(int firstArgumentsAmount, List<String > fieldNames) {
        this.firstArgumentsAmount = firstArgumentsAmount;
        this.fieldNames = fieldNames;
    }

    protected boolean checkInteractive(List<String> arguments) {
        return arguments.size() == getFirstArgumentsAmount();
    }

    protected boolean checkInLine(List<String> arguments) {
        return arguments.size() == fieldNames.size();
    }

    public abstract Object build(List<String> arguments, Invoker invoker) throws ObjectBuilderException;
    public abstract Object buildInLine(List<String> arguments, Invoker invoker) throws ObjectBuilderException;
}
