package ru.lexender.project.server.auth;

import ru.lexender.project.inbetween.Userdata;
import ru.lexender.project.server.Server;

import java.sql.SQLException;
import java.util.Random;

public class AuthWorker {
    private final Server server;

    private static String getSalt() {
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 12; ++i) {
            salt.append(random.nextInt(33, 93));
        }
        return salt.toString();
    }

    public AuthWorker(Server server) {
        this.server = server;
    }

    public void register(Userdata userdata) {

    }

}
