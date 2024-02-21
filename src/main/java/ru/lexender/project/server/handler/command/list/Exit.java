package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Exits the application. Saves history to file.
 */
public class Exit extends Command {
    public Exit() {
        super("exit", "Exits from the application");
    }
    public Response invoke(Invoker invoker) {
        String exitString = "Goodbye!";

        System.exit(0);
        return new Response(Prompt.ALL_OK, exitString);
    }
}
