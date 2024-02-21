package ru.lexender.project.server;

import lombok.Getter;
import ru.lexender.project.inbetween.Request;
import ru.lexender.project.server.invoker.IExecute;
import ru.lexender.project.server.storage.IStore;

@Getter
public class Server {
    private final IStore storage;
    private final IExecute invoker;

    public Server(IStore storage, IExecute invoker) {
        this.storage = storage;
        this.invoker = invoker;
    }
    public void getRequest(Request request) {
        invoker.execute();
    }
}

// TBD...
