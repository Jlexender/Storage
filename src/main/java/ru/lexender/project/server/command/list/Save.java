package ru.lexender.project.server.command.list;

import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.exception.file.transferer.StorageTransferException;
import ru.lexender.project.server.file.transferer.DefaultTransferer;
import ru.lexender.project.server.file.transferer.ITransfer;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Saves collection to file.
 */
public class Save extends Command {
    public Save() {
        super("save", "Writes current collection to chosen FileSystem");
    }
    public void execute(Invoker invoker) throws CommandExecutionException {
        try {
            ITransfer transferer = new DefaultTransferer(invoker.getFileSystem(), invoker.getStorage());
            transferer.transferOut();
            invoker.getSender().send("ok " + super.getAbbreviation());
        } catch (StorageTransferException exception) {
            throw new CommandExecutionException("Unable to save collection to file storage", this);

        }
    }
}
