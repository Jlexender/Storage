package ru.lexender.project.server.storage.transfering.file.json.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransformationException;
import ru.lexender.project.server.storage.transfering.file.io.IRead;
import ru.lexender.project.server.storage.transfering.file.io.Reader;
import ru.lexender.project.server.storage.transfering.file.json.adapter.LocalDateTimeAdapter;
import ru.lexender.project.server.storage.StorageObject;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses data from file to a list of StorageObject classes.
 * @see StorageObject
 */

public class GsonStorageParser extends Parser {


    public GsonStorageParser(File file) {
        super(file);
    }

    public List<StorageObject> parse() throws StorageTransformationException {
        try {
            IRead reader = new Reader();
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            Object[] parsedData = gson.fromJson(reader.read(getFile()), Object[].class);
            if (parsedData == null) throw new NullPointerException();
            List<StorageObject> formattedData = new ArrayList<>();

            for (Object object: parsedData) {
                StorageObject parsed = gson.fromJson(gson.toJson(object), StorageObject.class);
                formattedData.add(parsed);
            }

            return formattedData;
        } catch (Exception exception) {
            throw new StorageTransformationException("Can't parse from file storage");
        }
    }

}
