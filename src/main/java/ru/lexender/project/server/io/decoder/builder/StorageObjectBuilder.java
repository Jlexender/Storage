package ru.lexender.project.server.io.decoder.builder;

import ru.lexender.project.server.exception.handler.ObjectBuilderException;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.List;


/**
 * Tries to initialize an instance of StorageObject class by given arguments (in java.lang.String form)
 * @see ru.lexender.project.server.storage.object.StorageObject
 * @see ru.lexender.project.server.io.decoder.builder.ObjectBuilder
 */

public interface StorageObjectBuilder extends IBuild {
    public StorageObject<?> build(List<String> arguments, Invoker invoker) throws ObjectBuilderException;
    public StorageObject<?> buildInLine(List<String> arguments, Invoker invoker) throws ObjectBuilderException;
}
