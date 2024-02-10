package ru.lexender.project;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.controller.IControl;
import ru.lexender.project.console.handler.ConsoleHandler;
import ru.lexender.project.console.handler.IHandle;
import ru.lexender.project.console.handler.builder.list.StudyGroupBuilder;
import ru.lexender.project.console.receiver.ConsoleReceiver;
import ru.lexender.project.console.receiver.IReceive;
import ru.lexender.project.console.sender.ConsoleSender;
import ru.lexender.project.console.sender.ISend;
import ru.lexender.project.description.Color;
import ru.lexender.project.description.Coordinates;
import ru.lexender.project.description.Country;
import ru.lexender.project.description.FormOfEducation;
import ru.lexender.project.description.Person;
import ru.lexender.project.description.Semester;
import ru.lexender.project.description.StudyGroup;
import ru.lexender.project.exception.console.command.CommandExecutionException;
import ru.lexender.project.file.FileSystem;
import ru.lexender.project.file.enviroment.EnvironmentVariable;
import ru.lexender.project.file.transferer.DefaultTransferer;
import ru.lexender.project.file.transferer.ITransfer;
import ru.lexender.project.storage.TreeSetStorage;

import java.io.IOException;
import java.util.Arrays;

public class ConsoleApp implements Runnable {
    private final IReceive receiver;
    private final IControl controller;
    private final IHandle handler;
    private final ISend sender;

    public ConsoleApp(EnvironmentVariable pathToFile) {
        FileSystem fileSystem = new FileSystem(pathToFile);
        TreeSetStorage storage = new TreeSetStorage();

        ITransfer transferer = new DefaultTransferer(fileSystem, storage);

        this.receiver = new ConsoleReceiver();
        this.sender = new ConsoleSender();
        this.controller = new Controller(storage, fileSystem, sender);
        this.handler = new ConsoleHandler(new StudyGroupBuilder());

        try {
            transferer.transferIn();
        } catch (Exception exception) {
            sender.send(exception.getMessage());
        }
    }
    public void run() {

        Command init = new Command("init", "Initializes Console Application") {
            @Override
            public void execute(Controller controller) {
                String initString = "Welcome!";
                controller.getSender().send(initString);
            }
        };

        try {
            controller.execute(init);
        } catch (Exception exception) {
            sender.send(exception.getMessage());
        }

        for (;;) {
            try {
                controller.execute(handler.handle(receiver.receive()));
            } catch (Exception exception) {
                sender.send(exception.getMessage());
            }
        }
    }
}
