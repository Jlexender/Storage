package ru.lexender.project.inbetween.command.list;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.description.Semester;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Prints out semesterEnum fields in ascending order.
 */
public class PrintFieldAscendingSemesterEnum extends Command {

    public PrintFieldAscendingSemesterEnum() {
        super("print_field_ascending_semester_enum",
                "Prints out semesterEnum fields in ascending order");
    }
    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            Collection<StorageObject<?>> collection = invoker.getStorage().getCollectionCopy();

            Function<StorageObject<?>, Semester> toSemester = (c -> c.getObject().getSemesterEnum());

            List<Semester> semesters = collection.stream().map(toSemester).sorted().toList();
            for (Semester semester: semesters) {
                invoker.getSender().send(semester);
            }
        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }
}