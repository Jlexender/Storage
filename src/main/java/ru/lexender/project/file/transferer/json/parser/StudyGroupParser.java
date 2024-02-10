package ru.lexender.project.file.transferer.json.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.lexender.project.description.StudyGroup;
import ru.lexender.project.exception.file.transferer.StorageIOException;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.file.transferer.io.reader.IRead;
import ru.lexender.project.file.transferer.io.reader.ReaderViaScanner;
import ru.lexender.project.file.transferer.json.adapter.LocalDateTimeAdapter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

public class StudyGroupParser implements StorageObjectParser {
    private final File file;

    public StudyGroupParser(File file) {
        this.file = file;
    }

    public StudyGroup[] parse() throws StorageTransformationException {
        try {
            IRead reader = new ReaderViaScanner(file);
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            Optional<StudyGroup[]> parsedData = Optional.ofNullable(gson.fromJson(reader.read(), StudyGroup[].class));
            return parsedData.orElseGet(() -> new StudyGroup[0]);
        } catch (StorageIOException exception) {
            throw new StorageTransformationException("Can't parse from file storage");
        }
    }
}
