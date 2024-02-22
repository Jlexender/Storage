package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.List;

/**
 * Adds element to collection if it's less than collection minimum.
 */
public class AddIfMin extends ConstructorCommand {
    public AddIfMin(StorageObjectBuilder objectBuilder) {
        super("add_if_min",
                "Adds element to collection if it's less than minimal element",
                objectBuilder,
                12);
    }

    public Response invoke(Invoker invoker, List<String> arguments) {

        try {
            getInvalidArgId(invoker,arguments);

            if (arguments.size() != getArgumentsAmount()) {
                int i = arguments.size();
                setStatus(CommandStatus.WAITING_FOR_ARGUMENT);

                return new Response(Prompt.ADD_ARGUMENT,
                        String.format("Enter %s", getObjectBuilder().getFieldNames().get(i)));
            }


            StorageObject object = (StorageObject) getObjectBuilder().build(arguments);
            if (invoker.getStorage().getMin().compareTo(object) < 0) invoker.getStorage().add(object);

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
