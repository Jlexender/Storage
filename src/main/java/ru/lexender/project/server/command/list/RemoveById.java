package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.NonStaticCommand;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.List;

/**
 * Tries to remove the collection element with specified id.
 */
public class RemoveById extends NonStaticCommand {
    private final List<String> arguments;

    public RemoveById(List<String> arguments) {
        super("remove", "Removes collection element with specified id", 1);
        this.arguments = arguments;
    }
    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            if (arguments.size() != getArgumentsAmount())
                throw new CommandExecutionException("Wrong field amount");

            StorageObject<?> identifiedObject = invoker.getStorage().getById(Integer.parseInt(arguments.get(0)));
            if (!invoker.getStorage().remove(identifiedObject)) {
                String noObjectMessage = "No object with such id";
                invoker.getSender().send(noObjectMessage);
            }
        } catch (CommandExecutionException exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
