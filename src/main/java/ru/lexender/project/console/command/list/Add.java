package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.InteractiveCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.exception.console.handler.ObjectBuilderException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

/**
 * Adds new element to the collection.
 * @see ru.lexender.project.console.command.InteractiveCommand
 * @see ru.lexender.project.console.command.NonStaticCommand
 * @see ru.lexender.project.console.command.Command
 */
public class Add extends InteractiveCommand {
    private final List<String> firstArguments;

    public Add(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("add", "Adds new element to the collection", objectBuilder, 3);
        this.firstArguments = firstArguments;
    }


    public void execute(Controller controller) throws CommandExecutionException {
        try {
            StorageObject<?> object = (StorageObject<?>) getObjectBuilder().build(firstArguments, controller);
            controller.getStorage().add(object);
        } catch (ObjectBuilderException exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
