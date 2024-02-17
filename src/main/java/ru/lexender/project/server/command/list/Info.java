package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Prints out collection info.
 */
public class Info extends Command {

    public Info() {
        super("info", "Prints collection info");
    }
    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            invoker.getSender().send(String.format("Elements amount: %d", invoker.getStorage().size()));
            invoker.getSender().send(String.format("Last modified: %s", invoker.getFileSystem().getModificationDate()));
        } catch (Exception e) {
            throw new CommandExecutionException("An unexpected CommandExecutionException has been thrown", this);
        }
    }
}
