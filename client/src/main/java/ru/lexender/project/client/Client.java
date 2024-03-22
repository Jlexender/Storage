package ru.lexender.project.client;

import ru.lexender.project.client.io.Output;
import ru.lexender.project.client.io.receiver.IReceive;
import ru.lexender.project.client.io.respondent.IRespond;
import ru.lexender.project.client.io.transcriber.ITranscribe;
import ru.lexender.project.inbetween.Input;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Userdata;
import ru.lexender.project.inbetween.validator.Validator;



public record Client(IReceive receiver, IRespond respondent, ITranscribe transcriber, Userdata userdata) {

    public Request getRequest() {
        return new Request(receiver.receive(), userdata);
    }

    public Request getRequest(Validator validator) {
        Input input = receiver.receive();
        while (validator != null && !validator.test(input.get())) {
            respondent().respond(new Output("Not valid argument") {
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
