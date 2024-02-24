package ru.lexender.project.client;

import lombok.Getter;
import ru.lexender.project.client.io.receiver.IReceive;
import ru.lexender.project.client.io.respondent.IRespond;
import ru.lexender.project.client.io.transcriber.ITranscribe;
import ru.lexender.project.inbetween.Request;


@Getter
public class Client {
    private final IRespond respondent;
    private final IReceive receiver;
    private final ITranscribe transcriber;

    public Client(IReceive receiver, IRespond respondent, ITranscribe transcriber) {
        this.respondent = respondent;
        this.receiver = receiver;
        this.transcriber = transcriber;
    }

    public Request getRequest() {
        return new Request(receiver.receive());
    }
}
