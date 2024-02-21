package ru.lexender.project.server.invoker;

import lombok.Getter;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.storage.file.FileSystem;
import ru.lexender.project.server.storage.IStore;

/**
 * Class provided for IExecute interface.
 * NOTE: 1 request - 1 response (Invoker always respond)
 * @see IInvoke
 */

@Getter
public class Invoker implements IInvoke {
    private final IStore storage;
    private final FileSystem fileSystem;

    public Invoker(IStore storage, FileSystem fileSystem) {
        this.storage = storage;
        this.fileSystem = fileSystem;
    }

    public Response invoke(Command command) throws CommandExecutionException {
        return command.invoke(this);
    }
}
