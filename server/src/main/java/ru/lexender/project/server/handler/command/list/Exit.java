package ru.lexender.project.server.handler.command.list;

import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.transfering.ITransfer;

/**
 * Exits the application. Saves history to file.
 */
public class Exit extends Command {
    public Exit() {
        super("exit", "Exits from the application");
    }
    public Response invoke(Invoker invoker) {
        setStatus(CommandStatus.IN_PROCESS);
        try {
            ITransfer transferer = invoker.getTransferer();
            transferer.transferOut();

            Server.logger.info("Data transfer on disconnect OK");
        } catch (StorageTransferException exception) {
            Server.logger.error("Data transfer on disconnect FAILED");
        }

        String exitString = "Disconnected.";

        setStatus(CommandStatus.SUCCESS);
        return new Response(Prompt.DISCONNECTED, exitString);
    }
}
