package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.Collection;

/**
 * Prints out elements of the collection.
 */
public class Show extends Command {
    public Show() {
        super("show", "Prints collection elements");
    }
    public void execute(Invoker invoker) {
        Collection<? extends StorageObject<?>> collection = invoker.getStorage().getCollectionCopy();
        for (StorageObject<?> object: collection) {
            invoker.getSender().send(object);
        }
    }
}
