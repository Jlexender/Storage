package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.InteractiveCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.exception.console.handler.StorageObjectBuilderException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

public class Add extends InteractiveCommand {
    private final List<String> firstArguments;

    public Add(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("add", "Adds new element to the collection", objectBuilder, 12);
        this.firstArguments = firstArguments;
    }
    public void execute(Controller controller) throws CommandExecutionException {
        try {
            if (firstArguments.size() != super.getObjectBuilder().getFirstArgumentsAmount())
                throw new CommandExecutionException("Wrong field amount");
            StorageObject<?> object = getObjectBuilder().build(firstArguments, controller);
            controller.getStorage().add(object);
        } catch (StorageObjectBuilderException | CommandExecutionException exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
