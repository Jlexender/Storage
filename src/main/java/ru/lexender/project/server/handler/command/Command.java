package ru.lexender.project.server.handler.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Basic abstract class for command. Used for static commands.
 */

@Getter @ToString
@EqualsAndHashCode
public abstract class Command implements Comparable<Command> {
    private final String info;
    private final String abbreviation;
    private CommandStatus status;
    protected int argumentsAmount;

    public Command(@NonNull String abbreviation, @NonNull String info) {
        this.abbreviation = abbreviation;
        this.info = info;
        this.status = CommandStatus.NOT_USED;
        this.argumentsAmount = 0;
    }

    public String describe() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Command: ")
                .append(abbreviation)
                .append('\n')
                .append("Usage: ")
                .append(info)
                .append('\n')
                .append("Status: ")
                .append(status)
                .append('\n')
                .append("Arguments amount: ")
                .append(argumentsAmount);
        return stringBuilder.toString();
    }



    public abstract Response invoke(Invoker invoker);

    protected void setStatus(CommandStatus status) {
        this.status = status;
    }

    public int compareTo(Command command) {
        return abbreviation.compareTo(command.getAbbreviation());
    }
}
