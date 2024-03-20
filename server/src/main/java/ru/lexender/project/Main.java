package ru.lexender.project;

import ru.lexender.project.server.Server;
import ru.lexender.project.server.ServerBridge;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.Storage;
import ru.lexender.project.server.storage.transfering.db.PostgresQLTransferer;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverMessage = "Enter port number";
        System.out.println(serverMessage);
        IStore s = new Storage();
        Invoker i = new Invoker(s, new PostgresQLTransferer("postgres", "5432", s));
        Server server = new Server(s, i);
        ServerBridge sBridge = new ServerBridge(server, Integer.parseInt(scanner.next()));
        sBridge.run();
    }
}
