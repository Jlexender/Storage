package ru.lexender.project.client.io.sender;

import ru.lexender.project.client.io.Input;
import ru.lexender.project.inbetween.ClientBridge;
import ru.lexender.project.inbetween.Request;

public class Sender implements ISend {
    public void send(Input input, ClientBridge bridge) {
        bridge.send(new Request(input));
    }
}
