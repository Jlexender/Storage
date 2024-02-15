package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.history.HistoryParser;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;

import java.util.Collection;


/**
 * Initialization command.
 */
public class Init extends Command {

    public Init() {
        super("init", "Initializes Console Application");
    }
    @Override
    public void execute(Controller controller) throws CommandExecutionException {
        String initString = "Welcome!";
        controller.getSender().send(initString);


        try {
            HistoryParser parser = new HistoryParser();
            Collection<Command> commands = parser.parse();
            for (Command command: commands) {
                controller.getHistory().add(command);
            }
        } catch (StorageTransformationException exception) {
            throw new CommandExecutionException(exception);
        }

    }
}
