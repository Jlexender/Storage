package ru.lexender.project.server.command.list;

import ru.lexender.project.server.io.decoder.builder.StorageObjectBuilder;
import ru.lexender.project.server.command.InteractiveCommand;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.Collection;
import java.util.List;

/**
 * Removes all elements that are greater than the specified one.
 */
public class RemoveGreater extends InteractiveCommand {
    private final List<String> firstArguments;

    public RemoveGreater(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("remove_greater", "Removes elements that are greater than element", objectBuilder, 3);
        this.firstArguments = firstArguments;
    }

    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            StorageObject<?> createdObject = (StorageObject<?>) getObjectBuilder().build(firstArguments, invoker);
            Collection<StorageObject<?>> storage = invoker.getStorage().getCollectionCopy();
            for (StorageObject<?> object: storage) {
                if (object.compareTo(createdObject) < 0) invoker.getStorage().remove(object);
            }
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }

    }
}

