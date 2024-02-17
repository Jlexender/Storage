package ru.lexender.project.server.command.list;


import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.command.CommandGenerator;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Prints out all commands and their usage.
 */
public class Help extends Command {

    public Help() {
        super("help", "Prints available commands and its descriptions");
    }
    public void execute(Invoker invoker) {
        StringBuilder helpString = new StringBuilder("AVAILABLE COMMANDS:\n\n");
        for (Command command: CommandGenerator.getCommandList().values()) {
            helpString.append("Command: ").append(command.getAbbreviation())
                    .append('\n').append("Usage: ").append(command.getInfo()).append("\n\n");
        }
        invoker.getSender().send(helpString.toString());
    }
}
