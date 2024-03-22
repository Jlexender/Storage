package ru.lexender.project;

import ru.lexender.project.client.Client;
import ru.lexender.project.client.ClientBridge;
import ru.lexender.project.client.io.receiver.StringInputReceiver;
import ru.lexender.project.client.io.respondent.SoutRespondent;
import ru.lexender.project.client.io.transcriber.DefaultTranscriber;
import ru.lexender.project.inbetween.Userdata;

import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            Properties properties = new Properties();
            properties.load(new FileReader("client.cfg"));

            Client client = new Client(
                    new StringInputReceiver(),
                    new SoutRespondent(),
                    new DefaultTranscriber(),
                    Userdata.create(properties.getProperty("USERNAME"), properties.getProperty("PASSWORD"))
            );

            ClientBridge clientBridge = new ClientBridge(
                    client,
                    properties.getProperty("HOSTNAME"),
                    Integer.parseInt(properties.getProperty("PORT"))
            );
            clientBridge.run();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}
