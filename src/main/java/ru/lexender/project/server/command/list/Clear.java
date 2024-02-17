package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Clears the collection.
 */
public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the collection");
    }
    public void execute(Invoker invoker) {
        invoker.getStorage().clear();
    }
}
