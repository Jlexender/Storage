package ru.lexender.project;

import ru.lexender.project.client.io.StringInput;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.TreeSetStorage;
import ru.lexender.project.server.storage.file.FileSystem;
import ru.lexender.project.server.storage.file.variable.EnvironmentVariable;

import java.util.Scanner;

public class Main {
    static TreeSetStorage t = new TreeSetStorage();
    static FileSystem fs = new FileSystem(new EnvironmentVariable("ABC"));
    static Server server = new Server(t, new Invoker(t, fs));

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        for (;;) {
            see(s.nextLine());
        }

    }

    public static void see(String command) {
        Response res = server.getRequest(new Request(new StringInput(command)));
        System.out.println(res.getPrompt());
        System.out.println(res.getMessage());
    }
}