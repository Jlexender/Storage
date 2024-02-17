package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Exits the application. Saves history to file.
 */
public class Exit extends Command {
    public Exit() {
        super("exit", "Exits from the application");
    }
    public void execute(Invoker invoker) {
        String exitString = "Goodbye!";
        invoker.getSender().send(exitString);
        System.exit(0);
    }
}
