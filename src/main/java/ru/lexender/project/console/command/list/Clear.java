package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;

/**
 * Clears the collection.
 * @see ru.lexender.project.console.command.InteractiveCommand
 * @see ru.lexender.project.console.command.Command
 */
public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the collection");
    }
    public void execute(Controller controller) {
        controller.getStorage().clear();
    }
}
