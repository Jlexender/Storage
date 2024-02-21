package ru.lexender.project.client.io.sender;

import ru.lexender.project.client.io.Input;
import ru.lexender.project.inbetween.Bridge;

public interface ISend {
    public void send(Input input, Bridge bridge);
}
