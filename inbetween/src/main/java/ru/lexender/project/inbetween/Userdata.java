package ru.lexender.project.inbetween;

import com.google.common.hash.Hashing;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Getter
public class Userdata {
    private final String username, hash;

    private static final String pepper = "A3P)+g";

    public static Userdata create(String username, String password) throws IllegalAccessException {
        if (username.length() > 20)
            throw new IllegalAccessException("Username must be less than 20 characters in length");
        if (username.isBlank())
            throw new IllegalAccessException("Empty username is not allowed");

        String hash = Hashing.sha256()
                .hashString(pepper+password, StandardCharsets.UTF_8)
                .toString();

        return new Userdata(username, hash);
    }



    private Userdata(String username, String hash) {
        this.username = username;
        this.hash = hash;
    }

}
