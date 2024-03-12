package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.ArgumentedCommand;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.List;

/**
 * Prints out only elements that have the same name.
 */
public class FilterStartsWithName extends ArgumentedCommand {

    public FilterStartsWithName() {
        super("filter_starts_with_name", "Print object that has the same name", 1);
    }
    public Response invoke(Invoker invoker, List<String> arguments) {
        setStatus(CommandStatus.IN_PROCESS);

        try {
            if (arguments.size() != getArgumentsAmount()) {
                setStatus(CommandStatus.FAIL);
                return new Response(Prompt.INVALID_AMOUNT);
            }

            String name = arguments.get(0);
            List<StorageObject> filteredObjects = invoker.getStorage()
                    .getCollectionCopy()
                    .stream()
                    .filter((o) -> o.getObject().getName().equals(name))
                    .toList();
            StringBuilder stringBuilder = new StringBuilder();
            for (StorageObject object: filteredObjects) {
                stringBuilder.append(object.toString()).append('\n');
            }

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK, stringBuilder.toString());
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}
