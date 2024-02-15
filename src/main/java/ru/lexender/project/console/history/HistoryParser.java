package ru.lexender.project.console.history;

import lombok.NonNull;
import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.ConsoleHandler;
import ru.lexender.project.console.handler.IHandle;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.exception.console.handler.ObjectBuilderException;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.file.transferer.io.reader.IRead;
import ru.lexender.project.file.transferer.io.reader.ReaderViaScanner;
import ru.lexender.project.file.transferer.json.parser.Parser;
import ru.lexender.project.storage.object.StorageObject;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class HistoryParser extends Parser {
    public HistoryParser() {
        super(new File(".lab_history"));
    }

    public @NonNull Collection<Command> parse() throws StorageTransformationException {
        try {
            IRead reader = new ReaderViaScanner(getFile());
            IHandle handler = new ConsoleHandler(new StorageObjectBuilder() {
                @Override
                public StorageObject<?> build(List<String> arguments, Controller controller) throws ObjectBuilderException {
                    return StorageObject.nullObject;
                }

                @Override
                public StorageObject<?> buildInLine(List<String> arguments, Controller controller) throws ObjectBuilderException {
                    return StorageObject.nullObject;
                }
            });

            String allHistory = reader.read();
            Scanner historyScanner = new Scanner(allHistory);
            Collection<Command> commands = new LinkedList<>();

            while (historyScanner.hasNext()) {
                String line = historyScanner.nextLine();
                commands.add(handler.handle(line));
            }

            return commands;
        } catch (Exception exception) {
            throw new StorageTransformationException("Can't parse history");
        }
    }
}
