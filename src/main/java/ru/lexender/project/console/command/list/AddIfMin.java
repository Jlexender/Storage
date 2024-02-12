package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.InteractiveCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

/**
 * Adds element to collection if it's less than collection minimum.
 * @see ru.lexender.project.console.command.InteractiveCommand
 * @see ru.lexender.project.console.command.NonStaticCommand
 * @see ru.lexender.project.console.command.Command
 */
public class AddIfMin extends InteractiveCommand {
    private final List<String> firstArguments;

    public AddIfMin(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("add_if_min",
                "Adds element to collection if it's less than minimal element",
                objectBuilder, 3);
        this.firstArguments = firstArguments;
    }

    public void execute(Controller controller) throws CommandExecutionException {
        try {
            StorageObject<?> object = getObjectBuilder().build(firstArguments, controller);
            if (controller.getStorage().getMin().compareTo(object) < 0) controller.getStorage().add(object);
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }

    }
}
