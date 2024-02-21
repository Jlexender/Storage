package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.Collection;

/**
 * Prints out elements of the collection.
 */
public class Show extends Command {
    public Show() {
        super("show", "Prints collection elements");
    }

    public Response invoke(Invoker invoker) {
        try {
            Collection<? extends StorageObject> collection = invoker.getStorage().getCollectionCopy();
            String descriptions = collection.stream().map((o) -> o.toString() + "\n").toString();

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK, descriptions);
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}
