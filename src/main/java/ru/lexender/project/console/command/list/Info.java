package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.command.CommandExecutionException;

public class Info extends Command {

    public Info() {
        super("info", "Prints collection info");
    }
    public void execute(Controller controller) throws CommandExecutionException {
        try {
            controller.getSender().send(String.format("Elements amount: %d", controller.getStorage().size()));
            controller.getSender().send(String.format("Last modified: %s", controller.getFileSystem().getModificationDate()));
        } catch (Exception e) {
            throw new CommandExecutionException("An unexpected CommandExecutionException has been thrown", this);
        }
    }
}
