package ru.lexender.project.server.io.decoder;

import ru.lexender.project.inbetween.Request;

import java.util.Arrays;
import java.util.List;

/**
 * Request -> Command
 */
public class DefaultDecoder implements IDecode {
    public List<String> decode(Request request)  {
        String rawMessage = request.getRawMessage();
        return Arrays.stream(rawMessage.split(" ")).toList();
    }
}
