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
import java.util.List;

/**
 * Updates an element with specified id.
 */
public class UpdateId extends ConstructorCommand {
    private long id;

    public UpdateId(StorageObjectBuilder objectBuilder) {
        super("update_id", "Updates collection element by id", objectBuilder, 13);
    }


    public Response invoke(Invoker invoker, List<String> arguments) {
        try {
            if (getStatus() != CommandStatus.WAITING_FOR_ARGUMENT) {
                this.id = Long.parseLong(arguments.get(0));
                arguments = arguments.subList(1, arguments.size());
            }
            setStatus(CommandStatus.IN_PROCESS);

            int i = getInvalidArgId(arguments);
            if (i != getArgumentsAmount() - 1) {
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
