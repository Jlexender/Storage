package ru.lexender.project.server;

import ru.lexender.project.inbetween.Input;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.inbetween.Userdata;
import ru.lexender.project.server.connection.ServerBridge;
import ru.lexender.project.server.exception.command.CommandExecutionException;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.handler.DefaultHandler;
import ru.lexender.project.server.handler.command.AccessLevel;
import ru.lexender.project.server.handler.command.ArgumentedCommand;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.handler.command.CommandStatus;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.io.decoder.DefaultDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerConsole implements Runnable {
    private final ServerBridge serverBridge;

    public ServerConsole(ServerBridge serverBridge) {
       this.serverBridge = serverBridge;
    }

    public void run() {
        String rawInput;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            rawInput = scanner.nextLine();

            Request request;
            try {
                Userdata localUserdata = Userdata.create("local", "local");
                request = new Request(new Input(rawInput) {
                    @Override
                    public String get() {
                        return getInputObject().toString();
                    }
                }, localUserdata);
            } catch (IllegalAccessException exception) {
                Server.logger.error("Local user initialization FAILED: invalid credentials?");
                Server.logger.error(exception.getMessage());
                break;
            }

            DefaultDecoder decoder = new DefaultDecoder();
            DefaultHandler handler = new DefaultHandler();
            Invoker userInvoker = serverBridge.getServer().getInvoker();
            Invoker rootInvoker = new Invoker(userInvoker.getStorage(), userInvoker.getTransferer());

            List<Command> rootCommands = getCommands(handler);

            handler.registerCommands(rootCommands);
            rootInvoker.registerCommands(rootCommands);

            try {
                Command command = handler.handle(decoder.decode(request).get(0));
                Response response = rootInvoker.invoke(command, decoder.getArguments(request), "root");
                Server.logger.debug(response.toString());
            } catch (InvalidCommandException exception) {
                Server.logger.warn("Command identified as invalid");
            } catch (CommandExecutionException exception) {
                Server.logger.warn("{}", exception.getMessage());
            }
        }
    }

    private List<Command> getCommands(DefaultHandler handler) {
        Command send = new ArgumentedCommand("send", "Sends a message to specified username", 2) {
            @Override
            public Response invoke(Invoker invoker, List<String> args) {
                try {
                    setStatus(CommandStatus.IN_PROCESS);
                    String username = args.get(args.size() - 1);
                    List<String> argsToJoin = new ArrayList<>(args.subList(0, args.size() - 1));
                    String message = String.join(" ", argsToJoin);
                    serverBridge.getResponseProcessor().queryResponse(username,
                            new Response(
                                    Prompt.NOTIFICATION,
                                    message,
                                    serverBridge.
                                            getResponseProcessor().
                                            getLastResponses().get(username).getValidator()
                            )
                    );

                    setStatus(CommandStatus.SUCCESS);
                    return new Response(Prompt.ALL_OK);
                } catch (Exception exception) {
                    setStatus(CommandStatus.FAIL);
                    return new Response(Prompt.UNEXPECTED_ERROR, exception.getMessage());
                }
            }
        };

        Command invoke = new ArgumentedCommand("invoke", "Invokes a command by username", 0) {
            @Override
            public Response invoke(Invoker invoker, List<String> arguments) {
                try {
                    setStatus(CommandStatus.IN_PROCESS);
                    String username = arguments.get(0);
                    List<String> args = new ArrayList<>(arguments.subList(2, arguments.size()));
                    Command command = handler.handle(arguments.get(1));
                    invoker.invoke(command, args, username);
                    setStatus(CommandStatus.SUCCESS);
                    return new Response(Prompt.ALL_OK);
                } catch (Exception exception) {
                    setStatus(CommandStatus.FAIL);
                    return new Response(Prompt.UNEXPECTED_ERROR, exception.getMessage());
                }
            }
        };

        List<Command> rootCommands = AccessLevel.ALL;
        rootCommands.add(send);
        rootCommands.add(invoke);
        return rootCommands;
    }

}
