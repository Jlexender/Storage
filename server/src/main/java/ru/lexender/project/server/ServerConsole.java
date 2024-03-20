package ru.lexender.project.server;

import ru.lexender.project.inbetween.Input;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.handler.DefaultHandler;
import ru.lexender.project.server.handler.builder.list.PersonBuilder;
import ru.lexender.project.server.handler.builder.list.StudyGroupBuilder;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.list.Add;
import ru.lexender.project.server.handler.command.list.AddIfMin;
import ru.lexender.project.server.handler.command.list.Clear;
import ru.lexender.project.server.handler.command.list.CountGreaterThanGroupAdmin;
import ru.lexender.project.server.handler.command.list.Exit;
import ru.lexender.project.server.handler.command.list.FilterStartsWithName;
import ru.lexender.project.server.handler.command.list.Help;
import ru.lexender.project.server.handler.command.list.History;
import ru.lexender.project.server.handler.command.list.Info;
import ru.lexender.project.server.handler.command.list.PrintFieldAscendingSemesterEnum;
import ru.lexender.project.server.handler.command.list.RemoveById;
import ru.lexender.project.server.handler.command.list.RemoveGreater;
import ru.lexender.project.server.handler.command.list.Save;
import ru.lexender.project.server.handler.command.list.Show;
import ru.lexender.project.server.handler.command.list.UpdateId;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.io.decoder.DefaultDecoder;

import java.util.Scanner;

public class ServerConsole extends Thread {
    private final Server server;

    public ServerConsole(Server server) {
        super.setName("serverside");
        this.server = server;
    }
    public void run() {
        String rawInput;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            rawInput = scanner.nextLine();
            Request request = new Request(new Input(rawInput) {
                @Override
                public String get() {
                    return getInputObject().toString();
                }
            });

            Response response;
            DefaultDecoder decoder = new DefaultDecoder();
            DefaultHandler handler = new DefaultHandler();
            Invoker userInvoker = server.getInvoker();
            Invoker rootInvoker = new Invoker(userInvoker.getStorage(), userInvoker.getTransferer());

            Command[] rootCommands = {
                    new Help(),
                    new Info(),
                    new Show(),
                    new Exit(),
                    new Save(),
                    new History(),
                    new Add(new StudyGroupBuilder()),
                    new Clear(),
                    new UpdateId(new StudyGroupBuilder()),
                    new RemoveById(),
                    new AddIfMin(new StudyGroupBuilder()),
                    new RemoveGreater(new StudyGroupBuilder()),
                    new FilterStartsWithName(),
                    new CountGreaterThanGroupAdmin(new PersonBuilder()),
                    new PrintFieldAscendingSemesterEnum()
            };

            handler.registerCommands(rootCommands);
            rootInvoker.registerCommands(rootCommands);

            try {
                Command command = handler.handle(decoder.decode(request));
                Server.logger.info("Command handled as {}", command);
                response = rootInvoker.invoke(command, decoder.getArguments(request));
                Server.logger.debug(response.toString());
            } catch (InvalidCommandException exception) {
                Server.logger.warn("Command identified as invalid");
            } catch (CommandExecutionException exception) {
                Server.logger.warn("{}", exception.getMessage());
            }
        }
    }

}
