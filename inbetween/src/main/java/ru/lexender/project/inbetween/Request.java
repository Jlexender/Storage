package ru.lexender.project.inbetween;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter @ToString
public class Request implements Serializable {
    private final Userdata userdata;
    private final String rawMessage;

    public Request(Input input, Userdata userdata) {
        this.rawMessage = input.get();
        this.userdata = userdata;
    }
}

