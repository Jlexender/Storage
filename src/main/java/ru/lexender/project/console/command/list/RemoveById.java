package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.NonStaticCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

public class RemoveById extends NonStaticCommand {
    private final List<String> arguments;

    public RemoveById(List<String> arguments) {
        super("remove", "Removes collection element with specified id", 1);
        this.arguments = arguments;
    }
    public void execute(Controller controller) throws CommandExecutionException {
        try {
            if (arguments.size() != getArgumentsAmount())
                throw new CommandExecutionException("Wrong field amount");

            StorageObject<?> identifiedObject = controller.getStorage().getById(Integer.parseInt(arguments.get(0)));
            if (!controller.getStorage().remove(identifiedObject)) {
                String noObjectMessage = "No object with such id";
                controller.getSender().send(noObjectMessage);
            }
        } catch (CommandExecutionException exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
