package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.NonStaticCommand;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.List;

/**
 * Prints out only elements that have the same name.
 */
public class FilterStartsWithName extends NonStaticCommand {
    List<String> arguments;

    public FilterStartsWithName(List<String> arguments) {
        super("filter_starts_with_name", "Print object that has the same name", 1);
        this.arguments = arguments;
    }
    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            if (arguments.size() != getArgumentsAmount())
                throw new CommandExecutionException("Wrong field amount");

            String name = arguments.get(0);
            List<StorageObject<?>> filteredObjects = invoker.getStorage()
                    .getCollectionCopy()
                    .stream()
                    .filter((o) -> o.getObject().getName().equals(name))
                    .toList();
            for (StorageObject<?> object: filteredObjects) {
                invoker.getSender().send(object.toString());
            }
        } catch (CommandExecutionException exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
