package ru.lexender.project.file.transferer.json.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;
import ru.lexender.project.description.StudyGroup;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.file.transferer.io.reader.IRead;
import ru.lexender.project.file.transferer.io.reader.ReaderViaScanner;
import ru.lexender.project.file.transferer.json.adapter.LocalDateTimeAdapter;
import ru.lexender.project.storage.object.StorageObject;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudyGroupParser implements StorageObjectParser {
    private final File file;

    public StudyGroupParser(File file) {
        this.file = file;
    }

    public @NonNull List<StorageObject<?>> parse() throws StorageTransformationException {
        try {
            IRead reader = new ReaderViaScanner(file);
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            Optional<StudyGroup[]> parsedData = Optional.ofNullable(gson.fromJson(reader.read(), StudyGroup[].class));
            if (parsedData.isEmpty()) return new ArrayList<>();

            StudyGroup[] objects = parsedData.get();
            List<StorageObject<?>> castedObjects = new ArrayList<>();
            for (StudyGroup object: objects) {
                castedObjects.add(new StorageObject<>(object));
            }
            return castedObjects;
        } catch (Exception exception) {
            throw new StorageTransformationException("Can't parse from file storage");
        }
    }
}
