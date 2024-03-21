package ru.lexender.project.server.handler.command.list;


import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
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
        setStatus(CommandStatus.IN_PROCESS);
        try {
            StringBuilder helpString = new StringBuilder("AVAILABLE COMMANDS:\n\n");

            for (Command command: invoker.getAllowedCommands()) {
                helpString.append(command.describe()).append("\n\n");
            }

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.TRANSACTION_OK, helpString.toString());
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }

    }
}
