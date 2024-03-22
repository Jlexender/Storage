package ru.lexender.project.server.handler.builder;

import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.storage.StorageObject;

import java.util.List;


/**
 * Tries to initialize an instance of StorageObject class by given arguments (in java.lang.String form)
 * @see StorageObject
 * @see ru.lexender.project.server.handler.builder.ObjectBuilder
 */

public abstract class StorageObjectBuilder extends ObjectBuilder {

    public StorageObjectBuilder(int firstArgumentsAmount, List<String> fieldNames) {
        super(firstArgumentsAmount, fieldNames);
    }

    @Override
    public StorageObject build(List<String> args) {
        return StorageObject.nullObject;
    }

    public abstract StorageObject build(List<String> arguments, String author) throws IllegalAccessException, BuildFailedException;
}
