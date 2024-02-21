package ru.lexender.project.server.storage.file.transferer.json.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransformationException;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.file.transferer.json.adapter.LocalDateTimeAdapter;

import java.time.LocalDateTime;


public class GsonStorageSerializer implements ISerialize {
    protected final IStore storage;

    public GsonStorageSerializer(IStore storage) {
        this.storage = storage;
    }

    public String serialize() throws StorageTransformationException {
        try {
            Gson gson = new GsonBuilder().
                    excludeFieldsWithoutExposeAnnotation().
                    setPrettyPrinting().
                    registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            return gson.toJson(storage.getCollectionCopy().toArray());
        } catch (Exception exception) {
            throw new StorageTransformationException("Unexpected storage serializing exception");
        }
    }
}

