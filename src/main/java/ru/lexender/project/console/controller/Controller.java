package ru.lexender.project.console.controller;

import lombok.Getter;
import ru.lexender.project.console.command.Command;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.console.sender.ISend;
import ru.lexender.project.file.FileSystem;
import ru.lexender.project.storage.IStore;

@Getter
public class Controller implements IControl {
    IStore storage;
    FileSystem fileSystem;
    ISend sender;

    public Controller(IStore storage, FileSystem fileSystem, ISend sender) {
        this.storage = storage;
        this.fileSystem = fileSystem;
        this.sender = sender;
    }

    public void execute(Command command) throws CommandExecutionException {
        command.execute(this);
    }
}
