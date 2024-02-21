package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.description.Semester;
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
    public Response invoke(Invoker invoker) {
        try {
            Collection<StorageObject> collection = invoker.getStorage().getCollectionCopy();

            Function<StorageObject, Semester> toSemester = (c -> c.getObject().getSemesterEnum());

            List<Semester> semesters = collection.stream().map(toSemester).sorted().toList();
            StringBuilder stringBuilder = new StringBuilder();
            for (Semester semester: semesters) {
                stringBuilder.append(semester).append(' ');
            }

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK, stringBuilder.toString());
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}