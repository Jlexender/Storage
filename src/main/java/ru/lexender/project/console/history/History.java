package ru.lexender.project.console.history;

import lombok.Getter;
import ru.lexender.project.console.command.Command;
import ru.lexender.project.exception.file.transferer.StorageIOException;
import ru.lexender.project.file.transferer.io.writer.IWrite;
import ru.lexender.project.file.transferer.io.writer.WriteViaPrintWriter;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

@Getter
public class History {
    private final Queue<Command> collection;

    public History() {
        this.collection = new LinkedList<>();
    }

    public void add(Command command) {
        collection.add(command);
    }

    public void save() throws StorageIOException {
        IWrite writer = new WriteViaPrintWriter(new File(".lab_history"));
        StringBuilder historyString = new StringBuilder();
        for (Command command: collection) {
            historyString.append(command.getAbbreviation()).append('\n');
        }
        writer.write(historyString.toString());
    }
}
