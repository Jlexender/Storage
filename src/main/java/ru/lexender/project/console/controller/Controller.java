package ru.lexender.project.console.controller;

import lombok.Getter;
import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.handler.IHandle;
import ru.lexender.project.console.history.History;
import ru.lexender.project.console.receiver.IReceive;
import ru.lexender.project.console.sender.ISend;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.file.FileSystem;
import ru.lexender.project.storage.IStore;

import java.util.Stack;

/**
 * Class provided for IControl interface.
 * @see ru.lexender.project.console.controller.IControl
 */

@Getter
public class Controller implements IControl {
    private final History history;
    private final IStore storage;
    private final FileSystem fileSystem;
    private final ISend sender;
    private final IReceive receiver;
    private final IHandle handler;
    private final Stack<Command> executionScriptStack;

    public Controller(IStore storage, FileSystem fileSystem, ISend sender, IReceive receiver, IHandle handler) {
        this.storage = storage;
        this.fileSystem = fileSystem;
        this.sender = sender;
        this.receiver = receiver;
        this.handler = handler;
        this.history = new History();
        this.executionScriptStack = new Stack<>();
    }

    public void execute(Command command) throws CommandExecutionException {
        command.execute(this);
        history.add(command);
    }
}
