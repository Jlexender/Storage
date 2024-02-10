package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.NonStaticCommand;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.Optional;

public class Add extends NonStaticCommand {
    private final Optional<StorageObject> nullableObject;

    public Add(Optional<StorageObject> nullableObject, StorageObjectBuilder objectBuilder) {
        super("add", "Adds new element to the collection", objectBuilder);
        this.nullableObject = nullableObject;
        StringBuilder syntax = new StringBuilder(getAbbreviation());
        for (String field: objectBuilder.getOrderedFields()) {
            syntax.append(String.format(" <%s>", field));
        }
        super.syntax = syntax.toString();
    }
    public void execute(Controller controller) throws CommandExecutionException {
        if (nullableObject.isEmpty())
            throw new CommandExecutionException("Can't add to the collection", this);
        controller.getStorage().add(nullableObject.get());
        controller.getSender().send("ok " + super.getAbbreviation());
    }
}
