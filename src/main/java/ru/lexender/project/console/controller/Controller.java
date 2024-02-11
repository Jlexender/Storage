package ru.lexender.project.console.controller;

import lombok.Getter;
import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.receiver.IReceive;
import ru.lexender.project.console.sender.ISend;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.file.FileSystem;
import ru.lexender.project.storage.IStore;
import ru.lexender.project.storage.object.StorageObject;

@Getter
public class Controller implements IControl {
    IStore storage;
    FileSystem fileSystem;
    ISend sender;
    IReceive receiver;

    public Controller(IStore storage, FileSystem fileSystem, ISend sender, IReceive receiver) {
        this.storage = storage;
        this.fileSystem = fileSystem;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void execute(Command command) throws CommandExecutionException {
        command.execute(this);
    }
}
