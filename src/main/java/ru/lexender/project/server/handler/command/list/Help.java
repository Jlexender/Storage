package ru.lexender.project.server.handler.command.list;


import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandGenerator;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Prints out all commands and their usage.
 */
public class Help extends Command {

    public Help() {
        super("help", "Prints available commands and its descriptions");
    }

    public Response invoke(Invoker invoker) {
        try {
            StringBuilder helpString = new StringBuilder("AVAILABLE COMMANDS:\n\n");
            for (Command command: CommandGenerator.getCommandList().values()) {
                helpString.append("Command: ").append(command.getAbbreviation())
                        .append('\n').append("Usage: ").append(command.getInfo()).append("\n\n");
            }

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK, helpString.toString());
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }

    }
}
