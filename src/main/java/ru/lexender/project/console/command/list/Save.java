package ru.lexender.project.console.command.list;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.exception.file.transferer.StorageTransferException;
import ru.lexender.project.file.transferer.DefaultTransferer;
import ru.lexender.project.file.transferer.ITransfer;

import java.io.IOException;

public class Save extends Command {
    public Save() {
        super("save", "Writes current collection to chosen FileSystem.");
    }
    public void execute(Controller controller) throws CommandExecutionException {
        try {
            ITransfer transferer = new DefaultTransferer(controller.getFileSystem(), controller.getStorage());
            transferer.transferOut();
            controller.getSender().send("ok " + super.getAbbreviation());
        } catch (StorageTransferException exception) {
            throw new CommandExecutionException("Unable to save collection to file storage", this);

        }
    }
}
