package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.Collection;
import java.util.List;

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
            List<String> descriptions = collection.stream().map((o) -> o.toString() + "\n").toList();

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK, descriptions.toString());
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}
