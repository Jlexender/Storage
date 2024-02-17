package ru.lexender.project.client.handler.builder;

import ru.lexender.project.client.exception.handler.ObjectBuilderException;
import ru.lexender.project.server.invoker.Invoker;

import java.util.List;

/**
 * An interface for ObjectBuilder and StorageObjectBuilder generalization.
 * @see ru.lexender.project.client.handler.builder.ObjectBuilder
 * @see ru.lexender.project.client.handler.builder.StorageObjectBuilder
 */
public interface IBuild {
    public Object build(List<String> arguments, Invoker invoker) throws ObjectBuilderException;
    public Object buildInLine(List<String> arguments, Invoker invoker) throws ObjectBuilderException;
}
