package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Prints out collection info.
 */
public class Info extends Command {

    public Info() {
        super("info", "Prints collection info");
    }
    public Response invoke(Invoker invoker) {
        setStatus(CommandStatus.IN_PROCESS);
        try {

            String stringBuilder = String.format("Time: %s\nElements amount: %d\nUsername: %s\nElements created by user: %d\n",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss")),
                    invoker.getStorage().size(),
                    invoker.getCurrentUser(),
                    invoker.getStorage().getCollectionCopy()
                            .stream()
                            .filter(o -> o.getAuthor().equals(invoker.getCurrentUser()))
                            .count()
            );

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK, stringBuilder);
        } catch (Exception exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.UNEXPECTED_ERROR);
        }
    }
}
