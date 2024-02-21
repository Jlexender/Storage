package ru.lexender.project.server.invoker;

import lombok.Getter;
import ru.lexender.project.inbetween.Bridge;
import ru.lexender.project.client.io.receiver.IReceive;
import ru.lexender.project.server.command.Command;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.storage.file.FileSystem;
import ru.lexender.project.server.sender.ISend;
import ru.lexender.project.server.storage.IStore;

import java.util.Stack;

/**
 * Class provided for IExecute interface.
 * @see IExecute
 */

@Getter
public class Invoker implements IExecute {
    private final IStore storage;
    private final FileSystem fileSystem;
    private final ISend sender;
    private final IReceive receiver;
    private final Bridge handler;
    private final Stack<Command> executionScriptStack;

    public Invoker(IStore storage, FileSystem fileSystem, ISend sender, IReceive receiver, Bridge handler) {
        this.storage = storage;
        this.fileSystem = fileSystem;
        this.sender = sender;
        this.receiver = receiver;
        this.handler = handler;
        this.executionScriptStack = new Stack<>();
    }

    public void execute(Command command) throws CommandExecutionException {
        command.execute(this);
    }
}
