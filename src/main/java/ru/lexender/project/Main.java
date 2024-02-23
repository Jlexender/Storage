package ru.lexender.project;

import ru.lexender.project.client.Client;
import ru.lexender.project.client.io.respondent.SoutRespondent;
import ru.lexender.project.inbetween.Bridge;
import ru.lexender.project.inbetween.ClientBridge;
import ru.lexender.project.inbetween.ServerBridge;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.TreeSetStorage;
import ru.lexender.project.server.storage.file.FileSystem;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        IStore s = new TreeSetStorage();
        FileSystem fs = new FileSystem(new File("Storage.json"));
        Invoker i = new Invoker(s, fs);

        Server server = new Server(s, i);
        Client client = new Client(new SoutRespondent(), "localhost", 8080);


        ServerBridge sb = new ServerBridge(server, 8080);
        ClientBridge cb = new ClientBridge(client, "localhost", 8080);

        sb.start();
        cb.start();

    }
}