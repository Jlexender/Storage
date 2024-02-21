package ru.lexender.project.server.command.list;

import ru.lexender.project.server.io.decoder.builder.ObjectBuilder;
import ru.lexender.project.server.command.InteractiveCommand;
import ru.lexender.project.server.description.Person;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;

import java.util.List;

/**
 * Counts an amount of elements where groupAdmin is greater than specified groupAdmin.
 */
public class CountGreaterThanGroupAdmin extends InteractiveCommand {
    private final List<String> firstArguments;

    public CountGreaterThanGroupAdmin(List<String> firstArguments, ObjectBuilder objectBuilder) {
        super("counter_greater_than_group_admin",
                "Counts an amount of elements where groupAdmin is greater than specified groupAdmin",
                objectBuilder, 2);
        this.firstArguments = firstArguments;
    }


    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            Person builtPerson = (Person) getObjectBuilder().build(firstArguments, invoker);
            long counter = invoker.
                    getStorage().
                    getCollectionCopy().
                    stream().
                    filter((o) -> o.getObject().getGroupAdmin().compareTo(builtPerson) < 0)
                    .count();
            invoker.getSender().send(counter);
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
