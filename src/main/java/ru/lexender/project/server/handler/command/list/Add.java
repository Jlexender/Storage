package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.Arrays;
import java.util.List;

/**
 * Adds new element to the collection.
 */
public class Add extends ConstructorCommand {

    public Add(StorageObjectBuilder objectBuilder) {
        super("add", "Adds new element to the collection", objectBuilder, 12);
    }


    public Response invoke(Invoker invoker, List<String> arguments) {

        try {
            int i = getInvalidArgId(invoker, arguments);
            if (i != getArgumentsAmount()) {
                setStatus(CommandStatus.WAITING_FOR_ARGUMENT);

                StringBuilder responseString = new StringBuilder();

                if (i != arguments.size()) responseString.append("Invalid argument").append('\n');
                responseString.append(String.format("Add %s", getObjectBuilder().getFieldNames().get(i)));
                if (getObjectBuilder().suggest(i).length != 0)
                    responseString.append('\n').append(String.format("List: %s",
                            Arrays.stream(getObjectBuilder().suggest(i)).toList()));


                return new Response(Prompt.ADD_ARGUMENT,
                    responseString.toString());
            }


            StorageObject object = (StorageObject) getObjectBuilder().build(arguments);
            invoker.getStorage().add(object);

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
