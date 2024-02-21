package ru.lexender.project.server;

import lombok.Getter;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.inbetween.Response;
import ru.lexender.project.server.handler.DefaultHandler;
import ru.lexender.project.server.handler.IHandle;
import ru.lexender.project.server.invoker.IInvoke;
import ru.lexender.project.server.io.decoder.DefaultDecoder;
import ru.lexender.project.server.io.decoder.IDecode;
import ru.lexender.project.server.handler.builder.list.StudyGroupBuilder;
import ru.lexender.project.server.storage.IStore;


@Getter
public class Server {
    private final IStore storage;
    private final IInvoke invoker;

    public Server(IStore storage, IInvoke invoker) {
        this.storage = storage;
        this.invoker = invoker;
    }

// TBD!
    public void getRequest(Request request) {
        IDecode decoder = new DefaultDecoder();
        IHandle handler = new DefaultHandler(new StudyGroupBuilder());
        decoder.decode(request);
    }

}

