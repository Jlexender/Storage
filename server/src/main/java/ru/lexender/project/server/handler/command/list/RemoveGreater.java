package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.handler.builder.list.StudyGroupBuilder;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.StorageObject;

import java.util.Arrays;
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
        setStatus(CommandStatus.IN_PROCESS);
        try {
            int i = getInvalidArgId(arguments);
            if (i != getArgumentsAmount()) {
                setStatus(CommandStatus.WAITING_FOR_ARGUMENT);

                StringBuilder responseString = new StringBuilder();

                if (i != arguments.size()) responseString.append("Invalid argument").append('\n');
                responseString.append(String.format("Add %s", getObjectBuilder().getFieldNames().get(i)));
                if (getObjectBuilder().suggest(i).length != 0)
                    responseString.append('\n').append(String.format("List: %s",
                            Arrays.stream(getObjectBuilder().suggest(i)).toList()));


                return new Response(Prompt.ADD_ARGUMENT,
                        responseString.toString(), StudyGroupBuilder.validators.get(i));
            }


            StorageObject createdObject = (StorageObject) getObjectBuilder().build(arguments);
            Collection<StorageObject> storage = invoker.getStorage().getCollectionCopy();

            for (StorageObject object: storage) {
                if (object.compareTo(createdObject) < 0 && object.getAuthor().equals(invoker.getCurrentUser()))
                    invoker.getStorage().remove(object);
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

