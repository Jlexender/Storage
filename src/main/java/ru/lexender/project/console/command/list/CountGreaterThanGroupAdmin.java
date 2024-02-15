package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.InteractiveCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.ObjectBuilder;
import ru.lexender.project.description.Person;
import ru.lexender.project.exception.console.command.CommandExecutionException;

import java.util.List;

/**
 * Counts an amount of elements where groupAdmin is greater than specified groupAdmin.
 * @see ru.lexender.project.console.command.InteractiveCommand
 * @see ru.lexender.project.console.command.NonStaticCommand
 * @see ru.lexender.project.console.command.Command
 */
public class CountGreaterThanGroupAdmin extends InteractiveCommand {
    private final List<String> firstArguments;

    public CountGreaterThanGroupAdmin(List<String> firstArguments, ObjectBuilder objectBuilder) {
        super("counter_greater_than_group_admin",
                "Counts an amount of elements where groupAdmin is greater than specified groupAdmin",
                objectBuilder, 2);
        this.firstArguments = firstArguments;
    }


    public void execute(Controller controller) throws CommandExecutionException {
        try {
            Person builtPerson = (Person) getObjectBuilder().build(firstArguments, controller);
            long counter = controller.
                    getStorage().
                    getCollectionCopy().
                    stream().
                    filter((o) -> o.getObject().getGroupAdmin().compareTo(builtPerson) < 0)
                    .count();
            controller.getSender().send(counter);
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
