package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.storage.object.StorageObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Info extends Command {

    public Info() {
        super("info", "Prints collection info.");
    }
    public void execute(Controller controller) throws CommandExecutionException {
        try {
            Collection<StorageObject> storageCollection = controller.getStorage().getCollection();
            if (storageCollection.isEmpty()) {
                controller.getSender().send("No creation date (no objects in the collection)");
                return;
            }

            List<StorageObject> storageList = new ArrayList<>(storageCollection);
            storageList.sort(Comparator.comparing(StorageObject::getCreationDate));

            LocalDateTime creationDate = storageList.get(0).getCreationDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            controller.getSender().send("Creation date: " + creationDate.format(formatter));
        } catch (Exception e) {
            throw new CommandExecutionException("An unexpected CommandExecutionException has been thrown", this);
        }
    }
}
