package ru.lexender.project.client;

import lombok.Getter;
import ru.lexender.project.client.io.Output;
import ru.lexender.project.client.io.receiver.IReceive;
import ru.lexender.project.client.io.respondent.IRespond;
import ru.lexender.project.client.io.transcriber.ITranscribe;
import ru.lexender.project.inbetween.Input;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Userdata;
import ru.lexender.project.inbetween.validator.Validator;


@Getter
public class Client {
    private final IRespond respondent;
    private final IReceive receiver;
    private final ITranscribe transcriber;
    private final Userdata userdata;

    public Client(IReceive receiver, IRespond respondent, ITranscribe transcriber, Userdata userdata) {
        this.respondent = respondent;
        this.receiver = receiver;
        this.transcriber = transcriber;
        this.userdata = userdata;
    }

    public Request getRequest() {
        return new Request(receiver.receive(), userdata);
    }

    public Request getRequest(Validator validator) {
        Input input = receiver.receive();
        while (!validator.test(input.get())) {
            getRespondent().respond(new Output("Not valid argument") {
                @Override
                public String get() {
                    return getOutputObject().toString();
                }
            });
            input = receiver.receive();
        }
        return new Request(input, userdata);
    }
}
