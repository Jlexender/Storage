package ru.lexender.project.inbetween;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter @ToString
public class Userdata implements Serializable {
    private final String username, password;

    public static Userdata create(String username, String password) throws IllegalAccessException {
        if (username.length() > 20)
            throw new IllegalAccessException("Username must be less than 20 characters in length");
        if (username.isBlank())
            throw new IllegalAccessException("Empty username is not allowed");
        return new Userdata(username, password);
    }



    private Userdata(String username, String hash) {
        this.username = username;
        this.password = hash;
    }

}
