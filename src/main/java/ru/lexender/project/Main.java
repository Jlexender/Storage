package ru.lexender.project;

import ru.lexender.project.client.Client;
import ru.lexender.project.server.Server;


import java.util.Scanner;

public class Main {
//    static TreeSetStorage t = new TreeSetStorage();
//    static FileSystem fs = new FileSystem(new EnvironmentVariable("ABC"));
//    static Server server = new Server(t, new Invoker(t, fs));
//
//    public static void main(String[] args) {
//        Scanner s = new Scanner(System.in);
//        for (;;) {
//            see(s.nextLine());
//        }
//
//    }
//
//    public static void see(String command) {
//        Response res = server.getRequest(new Request(new StringInput(command)));
//        System.out.println(res.getPrompt());
//        System.out.println(res.getMessage());
//    }

    public static void startClient() {
        Client client = new Client();
        
    }

    public static void main(String[] args) {

    }
}