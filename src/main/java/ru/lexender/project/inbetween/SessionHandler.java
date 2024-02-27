package ru.lexender.project.inbetween;

import lombok.Getter;

import java.io.IOException;
import java.nio.channels.Selector;

@Getter
public class SessionHandler extends Thread {
    private final Selector selector;

    public SessionHandler(Selector selector) {
        this.selector = selector;
    }

    public void run() {
        try {
            while (true) {
                selector.select();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
