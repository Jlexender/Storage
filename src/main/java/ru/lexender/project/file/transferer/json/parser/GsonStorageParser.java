package ru.lexender.project.file.transferer.json.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.lexender.project.description.StudyGroup;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.file.transferer.io.reader.IRead;
import ru.lexender.project.file.transferer.io.reader.ReaderViaScanner;
import ru.lexender.project.file.transferer.json.adapter.LocalDateTimeAdapter;
import ru.lexender.project.storage.object.StorageInitializable;
import ru.lexender.project.storage.object.StorageObject;

import java.io.File;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parses data from file to a list of StorageObject classes.
 * @see ru.lexender.project.storage.object.StorageObject
 */

public class GsonStorageParser extends Parser {
    private final Type type;


    public GsonStorageParser(File file, Type type) {
        super(file);
        this.type = type;
    }

    public List<StorageObject<?>> parse() throws StorageTransformationException {
        try {
            IRead reader = new ReaderViaScanner(getFile());
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            Object[] parsedData = gson.fromJson(reader.read(), Object[].class);
            if (parsedData == null) throw new NullPointerException();
            List<StorageObject<?>> formattedData = new ArrayList<>();

            for (Object object: parsedData) {
                StorageObject<?> parsed = gson.fromJson(gson.toJson(object), type);
                formattedData.add(parsed);
            }

            return formattedData;
        } catch (Exception exception) {
            throw new StorageTransformationException("Can't parse from file storage");
        }
    }

}
