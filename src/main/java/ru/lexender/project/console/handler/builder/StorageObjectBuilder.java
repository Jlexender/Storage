package ru.lexender.project.console.handler.builder;

import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.handler.ObjectBuilderException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;


/**
 * Tries to initialize an instance of StorageObject class by given arguments (in java.lang.String form)
 * @see ru.lexender.project.storage.object.StorageObject
 * @see ru.lexender.project.console.handler.builder.ObjectBuilder
 */

public interface StorageObjectBuilder extends IBuild {
    public StorageObject<?> build(List<String> arguments, Controller controller) throws ObjectBuilderException;
    public StorageObject<?> buildInLine(List<String> arguments, Controller controller) throws ObjectBuilderException;
}
