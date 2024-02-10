package ru.lexender.project.console.sender;

import lombok.NonNull;

public interface ISend {
    public void send(Object object);
    default void sendf(String format, Object ... objects) {}
}
