package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.invoker.Invoker;

import java.util.Queue;

/**
 * Prints out all recent valid commands (without arguments).
 */
public class History extends Command {
    public History() {
        super("history", "Prints nine last executed commands");
    }
    public void execute(Invoker invoker) {}
}
