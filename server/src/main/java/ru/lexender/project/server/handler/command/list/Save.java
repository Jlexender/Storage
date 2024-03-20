package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.transfering.ITransfer;

/**
 * Saves collection to file.
 */
public class Save extends Command {
    public Save() {
        super("save", "Writes current collection to chosen storage");
    }
    public Response invoke(Invoker invoker) {
        setStatus(CommandStatus.IN_PROCESS);
        try {
            ITransfer transferer = invoker.getTransferer();
            transferer.transferOut();

            setStatus(CommandStatus.SUCCESS);
            return new Response(Prompt.ALL_OK, "Saved!");
        } catch (StorageTransferException exception) {
            setStatus(CommandStatus.FAIL);
            return new Response(Prompt.STORAGE_UNAVAILABLE);
        }
    }
}
