package ru.lexender.project.client.io.transcriber;

import ru.lexender.project.client.io.Output;
import ru.lexender.project.inbetween.Response;

public class DefaultTranscriber implements ITranscribe {

    public Output transcribe(Response response) {
        return new Output(response) {
            @Override
            public String get() {
                return String.format("Message: %s\nStatus: %s",response.getMessage(), response.getPrompt());
            }
        };
    }
}
