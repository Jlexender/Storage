package ru.lexender.project.server.command.list;

import ru.lexender.project.client.exception.handler.ObjectBuilderException;
import ru.lexender.project.client.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.command.InteractiveCommand;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.List;

/**
 * Adds new element to the collection.
 */
public class Add extends InteractiveCommand {
    private final List<String> firstArguments;

    public Add(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("add", "Adds new element to the collection", objectBuilder, 3);
        this.firstArguments = firstArguments;
    }


    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            StorageObject<?> object = (StorageObject<?>) getObjectBuilder().build(firstArguments, invoker);
            invoker.getStorage().add(object);
        } catch (ObjectBuilderException exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
