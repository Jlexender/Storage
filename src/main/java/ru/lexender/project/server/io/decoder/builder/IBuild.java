package ru.lexender.project.server.io.decoder.builder;

import ru.lexender.project.server.exception.handler.ObjectBuilderException;
import ru.lexender.project.server.invoker.Invoker;

import java.util.List;

/**
 * An interface for ObjectBuilder and StorageObjectBuilder generalization.
 * @see ru.lexender.project.server.io.decoder.builder.ObjectBuilder
 * @see ru.lexender.project.server.io.decoder.builder.StorageObjectBuilder
 */
public interface IBuild {
    public Object build(List<String> arguments, Invoker invoker) throws ObjectBuilderException;
    public Object buildInLine(List<String> arguments, Invoker invoker) throws ObjectBuilderException;
}
