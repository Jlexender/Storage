package ru.lexender.project;

import ru.lexender.project.client.Client;
import ru.lexender.project.client.ClientBridge;
import ru.lexender.project.client.io.receiver.StringInputReceiver;
import ru.lexender.project.client.io.respondent.SoutRespondent;
import ru.lexender.project.client.io.transcriber.DefaultTranscriber;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String clientMessage = "Enter hostname and port number";
        System.out.println(clientMessage);
        ru.lexender.project.client.Client client = new Client(
                new StringInputReceiver(),
                new SoutRespondent(),
                new DefaultTranscriber());
        ClientBridge cBridge = new ClientBridge(client, scanner.next(), Integer.parseInt(scanner.next()));
        cBridge.run();
    }

}
