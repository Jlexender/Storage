package ru.lexender.project.console.sender;

public class ConsoleSender implements ISend {
    public void send(Object message) {
        System.out.println(message);
    }
}
