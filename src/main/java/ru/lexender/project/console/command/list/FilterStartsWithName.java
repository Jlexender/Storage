package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.NonStaticCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

public class FilterStartsWithName extends NonStaticCommand {
    List<String> arguments;

    public FilterStartsWithName(List<String> arguments) {
        super("filter_starts_with_name", "Print object that has the same name", 1);
        this.arguments = arguments;
    }
    public void execute(Controller controller) throws CommandExecutionException {
        try {
            if (arguments.size() != getArgumentsAmount())
                throw new CommandExecutionException("Wrong field amount");

            String name = arguments.get(0);
            List<StorageObject<?>> filteredObjects = controller.getStorage()
                    .getCollectionCopy()
                    .stream()
                    .filter((o) -> o.getObject().getName().equals(name))
                    .toList();
            for (StorageObject<?> object: filteredObjects) {
                controller.getSender().send(object.toString());
            }
        } catch (CommandExecutionException exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
