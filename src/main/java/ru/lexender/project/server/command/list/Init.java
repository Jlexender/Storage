package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;


/**
 * Initialization command.
 */
public class Init extends Command {

    public Init() {
        super("init", "Initializes Console Application");
    }
    @Override
    public void execute(Invoker invoker) throws CommandExecutionException {}
}
