package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;


public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the collection");
    }
    public void execute(Controller controller) {
        controller.getStorage().clear();
    }
}
