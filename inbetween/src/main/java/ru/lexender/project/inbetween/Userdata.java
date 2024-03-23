package ru.lexender.project.inbetween;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter @ToString
public class Userdata implements Serializable {
    private final String username, password;

    public static Userdata create(String username, String password) throws IllegalAccessException {
        if (username.matches("[A-Za-z|\\d]{1,20}") && password.matches("[\\S]{1,20}"))
            return new Userdata(username, password);
        throw new IllegalAccessException("Invalid username or password format");
    }

    private Userdata(String username, String hash) {
        this.username = username;
        this.password = hash;
    }

}
