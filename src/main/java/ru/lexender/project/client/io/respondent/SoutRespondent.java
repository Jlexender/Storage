package ru.lexender.project.client.io.respondent;

import ru.lexender.project.client.io.Output;

public class SoutRespondent implements IRespond {
    public void respond(Output output) {
        System.out.println(output.get());
    }
}
