package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.file.transferer.StorageIOException;

/**
 * Exits the application. Saves history to file.
 * @see ru.lexender.project.console.command.InteractiveCommand
 * @see ru.lexender.project.console.command.NonStaticCommand
 * @see ru.lexender.project.console.command.Command
 */
public class Exit extends Command {
    public Exit() {
        super("exit", "Exits from the application");
    }
    public void execute(Controller controller) {
        String exitString = "Goodbye!";
        controller.getSender().send(exitString);
        controller.getHistory().add(new Exit());

        try {
            controller.getHistory().save();
        } catch (StorageIOException exception) {
            System.out.println("Can't save history");
        }
        System.exit(0);
    }
}
