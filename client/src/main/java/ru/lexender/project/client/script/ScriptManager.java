package ru.lexender.project.client.script;

import ru.lexender.project.client.Client;
import ru.lexender.project.client.io.Output;
import ru.lexender.project.inbetween.Input;
import ru.lexender.project.inbetween.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScriptManager {
    private final Client client;

    public ScriptManager(Client client) {
        this.client = client;
    }

    public boolean isScriptCommand(Request request) {
        return request.getInput().get().split(" ")[0].equals("execute");
    }

    public List<Request> getBatch(Input input) {
        String fileName = input.get().split(" ")[1];
        List<Request> inputs = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                inputs.add(new Request(new Input(scanner.nextLine()), client.userdata()));
            }
        } catch (FileNotFoundException exception) {
            client.respondent().respond(new Output("File not found!"));
        }
        return inputs;
    }
}
