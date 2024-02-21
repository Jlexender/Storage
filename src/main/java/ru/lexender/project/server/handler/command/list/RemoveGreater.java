package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.Collection;
import java.util.List;

/**
 * Removes all elements that are greater than the specified one.
 */
public class RemoveGreater extends ConstructorCommand {
    public RemoveGreater(StorageObjectBuilder objectBuilder) {
        super("remove_greater", "Removes elements that are greater than element", objectBuilder, 12);
    }

    public Response invoke(Invoker invoker, List<String> arguments) {
        if (!initialize(invoker, arguments)) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.INVALID_ARGUMENT);
        }

        try {

            if (arguments.size() != getArgumentsAmount()) {
                setStatus(CommandStatus.WAITING_FOR_ARGUMENT);


                int i = arguments.size();
                return new Response(Prompt.ADD_ARGUMENT,
                        String.format("Enter %s", getObjectBuilder().getFieldNames().get(i)));
            }


            StorageObject createdObject = (StorageObject) getObjectBuilder().build(arguments);
            Collection<StorageObject> storage = invoker.getStorage().getCollectionCopy();

            for (StorageObject object: storage) {
                if (object.compareTo(createdObject) < 0) invoker.getStorage().remove(object);
            }

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK);
        } catch (IllegalAccessException exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        } catch (BuildFailedException exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.INVALID_AMOUNT);
        }
    }
}

