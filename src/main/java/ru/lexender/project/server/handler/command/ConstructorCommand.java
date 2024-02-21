package ru.lexender.project.server.handler.command;

import lombok.Getter;
import ru.lexender.project.server.handler.builder.ObjectBuilder;
import ru.lexender.project.server.invoker.Invoker;

import java.util.List;

@Getter
public abstract class ConstructorCommand extends ArgumentedCommand {
    private final ObjectBuilder objectBuilder;
    protected List<String> recentArguments;

    public ConstructorCommand(String abbreviation, String info, ObjectBuilder objectBuilder, int argumentsAmount) {
        super(abbreviation, info, argumentsAmount);
        this.objectBuilder = objectBuilder;
    }

    protected final boolean initialize(Invoker invoker, List<String> arguments) {
        recentArguments = arguments;

        for (int i = 0; i < arguments.size(); ++i) {
            if (!getObjectBuilder().validateArgument(arguments.get(i), i))
                return false;
        }
        return true;
    }
}
