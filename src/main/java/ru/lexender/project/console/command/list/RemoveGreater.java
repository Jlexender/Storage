package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.InteractiveCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.Collection;
import java.util.List;

public class RemoveGreater extends InteractiveCommand {
    private final List<String> firstArguments;

    public RemoveGreater(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("remove_greater", "Removes elements that are greater than element", objectBuilder, 3);
        this.firstArguments = firstArguments;
    }

    public void execute(Controller controller) throws CommandExecutionException {
        try {
            StorageObject<?> createdObject = getObjectBuilder().build(firstArguments, controller);
            Collection<StorageObject<?>> storage = controller.getStorage().getCollectionCopy();
            for (StorageObject<?> object: storage) {
                if (object.compareTo(createdObject) < 0) controller.getStorage().remove(object);
            }
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }

    }
}

