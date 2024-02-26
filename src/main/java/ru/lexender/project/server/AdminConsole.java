package ru.lexender.project.server;

import ru.lexender.project.inbetween.Input;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.exception.io.handling.InvalidArgumentsException;
import ru.lexender.project.server.exception.io.handling.InvalidCommandException;
import ru.lexender.project.server.handler.DefaultHandler;
import ru.lexender.project.server.handler.IHandle;
import ru.lexender.project.server.handler.command.Command;
import ru.lexender.project.server.io.decoder.DefaultDecoder;
import ru.lexender.project.server.io.decoder.IDecode;

import java.util.Scanner;

public class AdminConsole extends Thread {
    private final Server server;

    public AdminConsole(Server server) {
        super.setName("serverside");
        this.server = server;
    }
    public void run() {
        String rawInput;
        Scanner scanner = new Scanner(System.in);
        for (;;) {
            rawInput = scanner.nextLine();
            Request request = new Request(new Input(rawInput) {
                @Override
                public String get() {
                    return getInputObject().toString();
                }
            });

            Response response;
            IDecode decoder = new DefaultDecoder();
            IHandle handler = new DefaultHandler();
            try {
                Command command = handler.handle(decoder.decode(request));
                Server.logger.info("Command handled as {}", command);
                response = server.getInvoker().invoke(command, decoder.getArguments(request));
            } catch (InvalidCommandException exception) {
                Server.logger.warn("Command identified as invalid");
                response = new Response(Prompt.INVALID_COMMAND);
            } catch (InvalidArgumentsException exception) {
                Server.logger.warn("Command arguments are not valid");
                response = new Response(Prompt.INVALID_AMOUNT);
            }

            Server.logger.debug(response.toString());
        }
    }

}
