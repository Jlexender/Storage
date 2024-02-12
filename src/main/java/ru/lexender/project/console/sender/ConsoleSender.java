package ru.lexender.project.console.sender;

/**
 * An implementation of ISend for the local console.
 * @see ru.lexender.project.console.sender.ISend
 */
public class ConsoleSender implements ISend {
    public void send(Object message) {
        System.out.println(message);
    }
}
