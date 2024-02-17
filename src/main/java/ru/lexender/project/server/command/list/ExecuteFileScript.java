package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.command.NonStaticCommand;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.invoker.Invoker;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Executes a script from certain file.
 * All script arguments are written is order that is provided for user.
 */
public class ExecuteFileScript extends NonStaticCommand {
    private final List<String> arguments;

    public ExecuteFileScript(List<String> arguments) {
        super("execute_script", "Executes the script with specified file name", 1);
        this.arguments = arguments;
    }
    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            invoker.getExecutionScriptStack().add(this);

            if (invoker.getExecutionScriptStack().size() > 100) {
                throw new CommandExecutionException("Recursion depth exceeded");
            }

            if (arguments.size() != getArgumentsAmount())
                throw new CommandExecutionException("Wrong field amount");

            File script = new File(arguments.get(0));
            Scanner scanner = new Scanner(script);

            while (scanner.hasNext()) {
                String command = scanner.nextLine();
                Command formattedCommand = invoker.getHandler().handle(command);
                invoker.execute(formattedCommand);
            }

            invoker.getExecutionScriptStack().clear();

        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }

}
