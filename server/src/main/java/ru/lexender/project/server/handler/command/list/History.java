package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;

import java.util.Stack;

/**
 * Prints out all recent valid commands (without arguments).
 */
public class History extends Command {
    public History() {
        super("history", "Prints last executed commands");
    }
    public Response invoke(Invoker invoker) {
        setStatus(CommandStatus.IN_PROCESS);
        try {
            Stack<Command> history = invoker.getHistory();

            StringBuilder stringBuilder = new StringBuilder();
            for (Command command: history) {
                stringBuilder.append(command.describe()).append("\n\n");
            }

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.TRANSACTION_OK, stringBuilder.toString());
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}
