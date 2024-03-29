package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.command.NonStaticCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.command.CommandExecutionException;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Executes a script from certain file.
 * All script arguments are written is order that is provided for user.
 * @see ru.lexender.project.console.command.InteractiveCommand
 * @see ru.lexender.project.console.command.NonStaticCommand
 * @see ru.lexender.project.console.command.Command
 */
public class ExecuteFileScript extends NonStaticCommand {
    private final List<String> arguments;

    public ExecuteFileScript(List<String> arguments) {
        super("execute_script", "Executes the script with specified file name", 1);
        this.arguments = arguments;
    }
    public void execute(Controller controller) throws CommandExecutionException {
        try {
            controller.getExecutionScriptStack().add(this);

            if (controller.getExecutionScriptStack().size() > 100) {
                throw new CommandExecutionException("Recursion depth exceeded");
            }

            if (arguments.size() != getArgumentsAmount())
                throw new CommandExecutionException("Wrong field amount");

            File script = new File(arguments.get(0));
            Scanner scanner = new Scanner(script);

            while (scanner.hasNext()) {
                String command = scanner.nextLine();
                Command formattedCommand = controller.getHandler().handle(command);
                controller.execute(formattedCommand);
            }

            controller.getExecutionScriptStack().clear();

        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }

}
