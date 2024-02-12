package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;

import java.util.Queue;

public class History extends Command {
    public History() {
        super("history", "Prints nine last executed commands");
    }
    public void execute(Controller controller) {
        Queue<Command> history = controller.getHistory();
        for (Command command: history) {
            controller.getSender().send(command.getAbbreviation());
        }
    }
}
