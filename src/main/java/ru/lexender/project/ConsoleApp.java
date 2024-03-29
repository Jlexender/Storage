package ru.lexender.project;

import ru.lexender.project.console.command.Command;
import ru.lexender.project.console.command.list.Init;
import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.controller.IControl;
import ru.lexender.project.console.handler.ConsoleHandler;
import ru.lexender.project.console.handler.IHandle;
import ru.lexender.project.console.handler.builder.list.StudyGroupBuilder;
import ru.lexender.project.console.receiver.ConsoleReceiver;
import ru.lexender.project.console.receiver.IReceive;
import ru.lexender.project.console.sender.ConsoleSender;
import ru.lexender.project.console.sender.ISend;
import ru.lexender.project.file.FileSystem;
import ru.lexender.project.file.transferer.DefaultTransferer;
import ru.lexender.project.file.transferer.ITransfer;
import ru.lexender.project.file.variable.EnvironmentVariable;
import ru.lexender.project.storage.TreeSetStorage;

/**
 *  The console application.
 */
public class ConsoleApp implements Runnable {
    private final IReceive receiver;
    private final IControl controller;
    private final IHandle handler;
    private final ISend sender;

    /**
     * Initializes ConsoleApp with EnvironmentVariable object.
     * @param pathToFile environment variable name
     * @see ru.lexender.project.file.variable.EnvironmentVariable
     */
    public ConsoleApp(EnvironmentVariable pathToFile) {
        FileSystem fileSystem = new FileSystem(pathToFile);
        TreeSetStorage storage = new TreeSetStorage();

        ITransfer transferer = new DefaultTransferer(fileSystem, storage);

        this.receiver = new ConsoleReceiver();
        this.sender = new ConsoleSender();
        this.handler = new ConsoleHandler(new StudyGroupBuilder());

        this.controller = new Controller(storage, fileSystem, sender, receiver, handler);

        try {
            transferer.transferIn();
        } catch (Exception exception) {
            sender.send(exception.getMessage());
        }
    }

    /**
     * Console initialization and execution.
     */
    public void run() {

        Command init = new Init();

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
