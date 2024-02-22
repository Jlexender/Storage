package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.handler.builder.ObjectBuilder;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.handler.command.ConstructorCommand;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.description.Person;

import java.util.List;

/**
 * Counts an amount of elements where groupAdmin is greater than specified groupAdmin.
 */
public class CountGreaterThanGroupAdmin extends ConstructorCommand {

    public CountGreaterThanGroupAdmin(ObjectBuilder objectBuilder) {
        super("counter_greater_than_group_admin",
                "Counts an amount of elements where groupAdmin is greater than specified groupAdmin",
                objectBuilder, 5);
    }


    public Response invoke(Invoker invoker, List<String> arguments) {

        try {
            getInvalidArgId(invoker, arguments);

            if (arguments.size() != getArgumentsAmount()) {
                int i = arguments.size();
                setStatus(CommandStatus.WAITING_FOR_ARGUMENT);

                return new Response(Prompt.ADD_ARGUMENT,
                        String.format("Enter %s", getObjectBuilder().getFieldNames().get(i)));
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
