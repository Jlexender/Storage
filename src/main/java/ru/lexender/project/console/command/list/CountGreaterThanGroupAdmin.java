package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.InteractiveCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.ObjectBuilder;
import ru.lexender.project.description.Person;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.exception.console.handler.ObjectBuilderException;

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
                objectBuilder, 12);
        this.firstArguments = firstArguments;
    }


    public void execute(Controller controller) throws CommandExecutionException {

        Person builtPerson;
        try {
            builtPerson = (Person) getObjectBuilder().build(firstArguments, controller);
        } catch (ObjectBuilderException exception) {
            try {
                builtPerson = (Person) getObjectBuilder().buildInLine(firstArguments, controller);
            } catch (ObjectBuilderException exception1) {
                throw new CommandExecutionException(exception);
            }
        }
        try {
            Person admin = builtPerson;
            long counter = controller.
                    getStorage().
                    getCollectionCopy().
                    stream().
                    filter((o) -> o.getObject().getGroupAdmin().compareTo(admin) < 0)
                    .count();
            controller.getSender().send(counter);
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }
}
