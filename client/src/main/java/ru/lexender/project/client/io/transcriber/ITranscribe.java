package ru.lexender.project.client.io.transcriber;

import ru.lexender.project.client.io.Output;
import ru.lexender.project.inbetween.Response;

/**
 * Response -> Output
 */
public interface ITranscribe {
    public Output transcribe(Response response);
}
