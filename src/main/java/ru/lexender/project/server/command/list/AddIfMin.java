package ru.lexender.project.server.command.list;

import ru.lexender.project.client.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.command.InteractiveCommand;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.List;

/**
 * Adds element to collection if it's less than collection minimum.
 */
public class AddIfMin extends InteractiveCommand {
    private final List<String> firstArguments;
    private final StorageObjectBuilder objectBuilder;

    public AddIfMin(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("add_if_min",
                "Adds element to collection if it's less than minimal element",
                objectBuilder, 3);
        this.firstArguments = firstArguments;
        this.objectBuilder = objectBuilder;
    }

    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            StorageObject<?> object = objectBuilder.build(firstArguments, invoker);
            if (invoker.getStorage().getMin().compareTo(object) < 0) invoker.getStorage().add(object);
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }

    }
}
