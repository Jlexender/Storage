package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.InteractiveCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.exception.console.handler.StorageObjectBuilderException;

import java.util.List;

public class UpdateId extends InteractiveCommand {
    private final List<String> firstArguments;

    public UpdateId(List<String> firstArguments, StorageObjectBuilder objectBuilder) {
        super("update", "Updates collection element with specified id", objectBuilder, 4);
        this.firstArguments = firstArguments;
    }

    public void execute(Controller controller) throws CommandExecutionException {
        try {
            if (firstArguments.size() != getArgumentsAmount())
                throw new CommandExecutionException(
                        String.format(
                                "Wrong field amount: %d arguments expected, got %d",
                                getArgumentsAmount(), firstArguments.size())
                );

            long id = Long.parseLong(firstArguments.get(0));
            firstArguments.remove(0);

            controller.getStorage().update(id, getObjectBuilder().build(firstArguments, controller));

        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }

    }
}
