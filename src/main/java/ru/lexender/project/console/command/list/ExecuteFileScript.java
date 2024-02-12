package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.command.NonStaticCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.command.CommandExecutionException;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class ExecuteFileScript extends NonStaticCommand {
    private final List<String> arguments;
    private final StorageObjectBuilder objectBuilder;
    public ExecuteFileScript(List<String> arguments, StorageObjectBuilder objectBuilder) {
        super("execute_script", "Executes the script with specified file name", 1);
        this.arguments = arguments;
        this.objectBuilder = objectBuilder;
    }
    public void execute(Controller controller) throws CommandExecutionException {
        try {
            if (arguments.size() != getArgumentsAmount())
                throw new CommandExecutionException("Wrong field amount");

            File script = new File(arguments.get(0));
            Scanner scanner = new Scanner(script);
            while (scanner.hasNext()) {
                String command = scanner.nextLine();
                try {
                    controller.execute(controller.getHandler().handle(command));
                } catch (CommandExecutionException exception) {
                    controller.getSender().send(String.format("Execution of '%s' failed.", command));
                }
            }

        } catch (Exception exception) {
            throw new CommandExecutionException(exception);
        }
    }

}
