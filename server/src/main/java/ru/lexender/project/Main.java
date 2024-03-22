package ru.lexender.project;

import org.checkerframework.checker.units.qual.A;
import ru.lexender.project.inbetween.Userdata;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.ServerBridge;
import ru.lexender.project.server.auth.AuthWorker;
import ru.lexender.project.server.auth.UserdataBridge;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.Storage;
import ru.lexender.project.server.storage.transfering.db.PostgresQLTransferer;

import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("server.cfg"));

            IStore s = new Storage();
            Invoker invoker = new Invoker(
                    s,
                    new PostgresQLTransferer(
                            properties.getProperty("DB_NAME"),
                            Integer.parseInt(properties.getProperty("DB_PORT")),
                            s,
                            properties.getProperty("DB_USERNAME"),
                            properties.getProperty("DB_PASSWORD")
                    )
            );

            Server server = new Server(
                    invoker,
                    new AuthWorker(new UserdataBridge(
                            properties.getProperty("DB_NAME"),
                            Integer.parseInt(properties.getProperty("DB_PORT")),
                            properties.getProperty("DB_USERNAME"),
                            properties.getProperty("DB_PASSWORD")
                    ))
            );

            ServerBridge serverBridge = new ServerBridge(
                    server,
                    Integer.parseInt(properties.getProperty("PORT"))
            );

            serverBridge.run();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
