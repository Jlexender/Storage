package ru.lexender.project.console.controller;

import lombok.Getter;
import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.receiver.IReceive;
import ru.lexender.project.console.sender.ISend;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.file.FileSystem;
import ru.lexender.project.storage.IStore;

import java.util.LinkedList;
import java.util.Queue;

@Getter
public class Controller implements IControl {
    private final Queue<Command> history = new LinkedList<>();

    private final IStore storage;
    private final FileSystem fileSystem;
    private final ISend sender;
    private final IReceive receiver;

    public Controller(IStore storage, FileSystem fileSystem, ISend sender, IReceive receiver) {
        this.storage = storage;
        this.fileSystem = fileSystem;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void execute(Command command) throws CommandExecutionException {
        command.execute(this);
        history.add(command);
        if (history.size() > 9) history.remove();
    }
}
