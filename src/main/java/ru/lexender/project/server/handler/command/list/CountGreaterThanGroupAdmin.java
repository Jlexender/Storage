package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.handler.builder.ObjectBuilder;
import ru.lexender.project.server.handler.builder.list.PersonBuilder;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.description.Person;

import java.util.Arrays;
import java.util.List;

/**
 * Counts an amount of elements where groupAdmin is greater than specified groupAdmin.
 */
public class CountGreaterThanGroupAdmin extends ConstructorCommand {

    public CountGreaterThanGroupAdmin(ObjectBuilder objectBuilder) {
        super("count_greater_than_group_admin",
                "Counts an amount of elements where groupAdmin is greater than specified groupAdmin",
                objectBuilder, 5);
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
                        responseString.toString(), PersonBuilder.validators.get(i));
            }

            Person builtPerson = (Person) getObjectBuilder().build(arguments);

            long counter = invoker.
                    getStorage().
                    getCollectionCopy().
                    stream().
                    filter((o) -> o.getObject().getGroupAdmin().compareTo(builtPerson) < 0)
                    .count();

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK, String.valueOf(counter));
        } catch (IllegalAccessException exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        } catch (BuildFailedException exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.INVALID_AMOUNT);
        }
    }
}
