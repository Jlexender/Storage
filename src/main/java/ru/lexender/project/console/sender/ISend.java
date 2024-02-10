package ru.lexender.project.console.sender;

public interface ISend {
    public void send(Object object);
    default void sendf(String format, Object ... objects) {}
}
