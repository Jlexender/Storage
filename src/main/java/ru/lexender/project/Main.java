package ru.lexender.project;

import ru.lexender.project.client.Client;
import ru.lexender.project.client.io.receiver.StringInputReceiver;
import ru.lexender.project.client.io.respondent.SoutRespondent;
import ru.lexender.project.client.io.transcriber.DefaultTranscriber;
import ru.lexender.project.inbetween.ClientBridge;
import ru.lexender.project.inbetween.ServerBridge;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.TreeSetStorage;
import ru.lexender.project.server.storage.file.FileSystem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String chooseMessage = """
                Choose an option:
                1. Client
                2. Server
                """;
            System.out.println(chooseMessage);
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            switch (msg) {
                case "1":
                    String clientMessage = "Enter hostname and port number";
                    System.out.println(clientMessage);
                    Client client = new Client(
                            new StringInputReceiver(),
                            new SoutRespondent(),
                            new DefaultTranscriber());
                    ClientBridge cBridge = new ClientBridge(client, scanner.next(), Integer.parseInt(scanner.next()));
                    cBridge.run();
                    break;
                case "2":
                    String serverMessage = "Enter port number";
                    System.out.println(serverMessage);
                    IStore s = new TreeSetStorage();
                    FileSystem fs = new FileSystem(new File("Storage.json"));
                    Invoker i = new Invoker(s, fs);
                    Server server = new Server(s, i);
                    ServerBridge sBridge = new ServerBridge(server, Integer.parseInt(scanner.next()));
                    sBridge.run();
                    break;
                default:
                    throw new IOException("Client/server initialization failed");
            }
        } catch (IOException exception) {}
    }
}