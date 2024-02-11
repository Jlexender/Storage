package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.command.CommandGenerator;
import ru.lexender.project.console.controller.Controller;

public class Help extends Command {

    public Help() {
        super("help", "Prints available commands and its descriptions.");
    }
    public void execute(Controller controller) {
        StringBuilder helpString = new StringBuilder("AVAILABLE COMMANDS:\n\n");
        for (Command command: CommandGenerator.getCommandList().values()) {
            helpString.append("Command: ").append(command.getAbbreviation())
                    .append('\n').append("Info: ").append(command.getInfo()).append('\n');
        }
        controller.getSender().send(helpString.toString());
    }
}
