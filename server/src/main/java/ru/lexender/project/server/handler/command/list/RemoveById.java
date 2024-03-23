package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.ArgumentedCommand;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.StorageObject;

import java.util.List;

/**
 * Tries to remove the collection element with specified id.
 */
public class RemoveById extends ArgumentedCommand {

    public RemoveById() {
        super("remove", "Removes collection element with specified id", 1);
    }
    public Response invoke(Invoker invoker, List<String> arguments) {
        setStatus(CommandStatus.IN_PROCESS);
        try {
            if (arguments.size() != getArgumentsAmount()) {
                setStatus(CommandStatus.FAIL);
                return new Response(Prompt.INVALID_AMOUNT);
            }

            long id = Long.parseLong(arguments.get(0));
            StorageObject object = invoker.getStorage().getById(id);

            if (!object.getAuthor().equals(invoker.getCurrentUser())) {
                setStatus(CommandStatus.FAIL);
                return new Response(Prompt.ACCESS_DENIED);
            }

            invoker.getStorage().remove(object);

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK);
        } catch (NumberFormatException exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.INVALID_ARGUMENT);
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}
