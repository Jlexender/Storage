package ru.lexender.project;

import ru.lexender.project.server.Server;
import ru.lexender.project.server.ServerBridge;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.TreeSetStorage;
import ru.lexender.project.server.storage.file.FileSystem;

import java.io.File;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverMessage = "Enter port number";
        System.out.println(serverMessage);
        IStore s = new TreeSetStorage();
        FileSystem fs = new FileSystem(new File("Storage.json"));
        Invoker i = new Invoker(s, fs);
        Server server = new Server(s, i);
        ServerBridge sBridge = new ServerBridge(server, Integer.parseInt(scanner.next()));
        sBridge.run();
    }
}
