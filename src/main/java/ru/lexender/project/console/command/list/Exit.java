package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;

public class Exit extends Command {
    public Exit() {
        super("exit", "Exits from the application");
    }
    public void execute(Controller controller) {
        String exitString = "Goodbye!";
        controller.getSender().send(exitString);
        System.exit(0);
    }
}
