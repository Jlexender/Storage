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
 * Updates an element with specified id.
 */
public class UpdateId extends ConstructorCommand {

    public UpdateId(StorageObjectBuilder objectBuilder) {
        super("add", "Adds new element to the collection", objectBuilder, 12);
    }


    public Response invoke(Invoker invoker, List<String> arguments) {
        try {
            long id = Long.parseLong(arguments.get(0));



            List<String> buildArguments = arguments.subList(1, arguments.size());

            if (!initialize(invoker, buildArguments)) {
                setStatus(CommandStatus.FAIL);
                return new Response(Prompt.INVALID_ARGUMENT);
            }

            if (buildArguments.size() != getArgumentsAmount()-1) {
                setStatus(CommandStatus.WAITING_FOR_ARGUMENT);


                int i = getObjectBuilder().getFieldNames().size()-1;
                return new Response(Prompt.ADD_ARGUMENT,
                        String.format("Enter %s", getObjectBuilder().getFieldNames().get(i)));
            }


            StorageObject object = (StorageObject) getObjectBuilder().build(arguments);
            invoker.getStorage().getById(id).update(object);

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
