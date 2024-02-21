package ru.lexender.project.server.handler.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.invoker.Invoker;

/**
 * Basic abstract class for command. Used for static commands.
 */

@Getter
@EqualsAndHashCode
public abstract class Command {
    private final String info;
    private final String abbreviation;
    private CommandStatus status;

    public Command(@NonNull String abbreviation, @NonNull String info) {
        this.abbreviation = abbreviation;
        this.info = info;
        this.status = CommandStatus.IN_QUEUE;
    }

    public abstract Response invoke(Invoker invoker);

    protected void setStatus(CommandStatus status) {
        this.status = status;
    }
}
