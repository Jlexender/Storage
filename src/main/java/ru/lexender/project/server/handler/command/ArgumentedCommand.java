package ru.lexender.project.server.handler.command;

import lombok.Getter;
import ru.lexender.project.inbetween.Prompt;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.invoker.Invoker;

import java.util.List;

@Getter
public abstract class ArgumentedCommand extends Command {
    public ArgumentedCommand(String abbreviation, String info, int argumentsAmount) {
        super(abbreviation, info);
        this.argumentsAmount = argumentsAmount;
    }

    @Override
    public final Response invoke(Invoker invoker) {
        return new Response(Prompt.INVALID_ARGUMENT);
    }

    public abstract Response invoke(Invoker invoker, List<String> arguments);
}
