package ru.lexender.project.console.sender;

public class ConsoleSender implements ISend {
    public void sendf(String format, Object ... args) {
        System.out.printf(format, args);
    }

    public void send(Object object) {
        System.out.println(object);
    }
}
