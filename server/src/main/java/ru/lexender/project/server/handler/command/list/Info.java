package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Prints out collection info.
 */
public class Info extends Command {

    public Info() {
        super("info", "Prints collection info");
    }
    public Response invoke(Invoker invoker) {
        setStatus(CommandStatus.IN_PROCESS);
        try {

            String stringBuilder = String.format("Elements amount: %d", invoker.getStorage().size()) +
                    '\n';

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.TRANSACTION_OK, stringBuilder);
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}
