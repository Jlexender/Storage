package ru.lexender.project.console.handler.builder;

import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.handler.ObjectBuilderException;

import java.util.List;

/**
 * An interface for ObjectBuilder and StorageObjectBuilder generalization.
 * @see ru.lexender.project.console.handler.builder.ObjectBuilder
 * @see ru.lexender.project.console.handler.builder.StorageObjectBuilder
 */
public interface IBuild {
    public Object build(List<String> arguments, Controller controller) throws ObjectBuilderException;
    public Object buildInLine(List<String> arguments, Controller controller) throws ObjectBuilderException;
}
