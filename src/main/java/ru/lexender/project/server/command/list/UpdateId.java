package ru.lexender.project.server.command.list;

import ru.lexender.project.server.io.decoder.builder.StorageObjectBuilder;
import ru.lexender.project.server.command.InteractiveCommand;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.List;

/**
 * Updates an element with specified id.
 */
public class UpdateId extends InteractiveCommand {
    private final List<String> firstArguments;

    public UpdateId(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("update", "Updates collection element with specified id", objectBuilder, 13);
        this.firstArguments = firstArguments;
    }

    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            long id = Long.parseLong(firstArguments.get(0));
            firstArguments.remove(0);

            StorageObject<?> foundOne = invoker.getStorage().getById(id);
            StorageObject<?> newOne = (StorageObject<?>) getObjectBuilder().build(firstArguments, invoker);
            foundOne.update(newOne);
        } catch (IllegalArgumentException exception) {
            throw new CommandExecutionException("Invalid command arguments");
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
