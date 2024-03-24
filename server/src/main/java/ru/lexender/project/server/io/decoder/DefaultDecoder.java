package ru.lexender.project.server.io.decoder;

import ru.lexender.project.inbetween.Request;

import java.util.Arrays;
import java.util.List;

/**
 * Request -> DATA
 */
public class DefaultDecoder implements IDecode {
    public List<String> decode(Request request)  {
        String rawMessage = request.getInput().get();
        return Arrays.stream(rawMessage.split(" ")).toList();
    }

    public List<String> getArguments(Request request) {
        List<String> message = decode(request);
        return message.subList(1, message.size());
    }

    public List<String> decode(String rawMessage) {
        return Arrays.stream(rawMessage.split(" ")).toList();
    }
}
