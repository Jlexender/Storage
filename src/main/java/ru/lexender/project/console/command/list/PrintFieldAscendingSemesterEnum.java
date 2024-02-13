package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.description.Semester;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Prints out semesterEnum fields in ascending order.
 * @see ru.lexender.project.console.command.InteractiveCommand
 * @see ru.lexender.project.console.command.NonStaticCommand
 * @see ru.lexender.project.console.command.Command
 */
public class PrintFieldAscendingSemesterEnum extends Command {

    public PrintFieldAscendingSemesterEnum() {
        super("print_field_ascending_semester_enum",
                "Prints out semesterEnum fields in ascending order");
    }
    public void execute(Controller controller) throws CommandExecutionException {
        try {
            Collection<StorageObject<?>> collection = controller.getStorage().getCollectionCopy();

            Function<StorageObject<?>, Semester> toSemester = (c -> c.getObject().getSemesterEnum());

            List<Semester> semesters = collection.stream().map(toSemester).sorted().toList();
            for (Semester semester: semesters) {
                controller.getSender().send(semester);
            }
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }
}