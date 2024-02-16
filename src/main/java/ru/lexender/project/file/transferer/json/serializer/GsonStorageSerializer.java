package ru.lexender.project.file.transferer.json.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.file.transferer.json.adapter.LocalDateTimeAdapter;
import ru.lexender.project.storage.IStore;
import ru.lexender.project.storage.object.StorageObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Class provided with ISerialize in order to serialize IStore classes. Implementation with Google Gson.
 * @see ru.lexender.project.file.transferer.json.serializer.ISerialize
 */
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

