package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.storage.object.StorageObject;

import java.util.Collection;

/**
 * Prints out elements of the collection.
 * @see ru.lexender.project.console.command.InteractiveCommand
 * @see ru.lexender.project.console.command.NonStaticCommand
 * @see ru.lexender.project.console.command.Command
 */
public class Show extends Command {
    public Show() {
        super("show", "Prints collection elements");
    }
    public void execute(Controller controller) {
        Collection<? extends StorageObject<?>> collection = controller.getStorage().getCollectionCopy();
        for (StorageObject<?> object: collection) {
            controller.getSender().send(object);
        }
    }
}
