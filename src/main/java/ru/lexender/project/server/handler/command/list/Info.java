package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.exception.command.CommandExecutionException;
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
        try {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder
                    .append(String.format("Elements amount: %d", invoker.getStorage().size()))
                    .append('\n')
                    .append(String.format("Last modified: %s", invoker.getFileSystem().getModificationDate()));

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK, stringBuilder.toString());
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}
