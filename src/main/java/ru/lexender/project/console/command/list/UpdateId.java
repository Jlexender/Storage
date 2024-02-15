package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.InteractiveCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

/**
 * Updates an element with specified id.
 * @see ru.lexender.project.console.command.InteractiveCommand
 * @see ru.lexender.project.console.command.NonStaticCommand
 * @see ru.lexender.project.console.command.Command
 */
public class UpdateId extends InteractiveCommand {
    private final List<String> firstArguments;

    public UpdateId(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("update", "Updates collection element with specified id", objectBuilder, 13);
        this.firstArguments = firstArguments;
    }

    public void execute(Controller controller) throws CommandExecutionException {
        try {
            long id = Long.parseLong(firstArguments.get(0));
            firstArguments.remove(0);

            StorageObject<?> foundOne = controller.getStorage().getById(id);
            StorageObject<?> newOne = (StorageObject<?>) getObjectBuilder().build(firstArguments, controller);
            foundOne.update(newOne);
        } catch (IllegalArgumentException exception) {
            throw new CommandExecutionException("Invalid command arguments");
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
