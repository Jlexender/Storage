package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Clears the collection.
 */
public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the collection");
    }
    public Response invoke(Invoker invoker) {
        setStatus(CommandStatus.IN_PROCESS);
        try {
            invoker.getStorage().removeAll(invoker.getStorage().getCollectionCopy()
                    .stream()
                    .filter(o -> o.getAuthor().equals(invoker.getCurrentUser()))
                    .toList());

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK);
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}
