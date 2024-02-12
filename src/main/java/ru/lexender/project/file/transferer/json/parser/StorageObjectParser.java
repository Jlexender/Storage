package ru.lexender.project.file.transferer.json.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.description.StudyGroup;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.file.transferer.io.reader.IRead;
import ru.lexender.project.file.transferer.io.reader.ReaderViaScanner;
import ru.lexender.project.file.transferer.json.adapter.LocalDateTimeAdapter;
import ru.lexender.project.storage.object.StorageInitializable;
import ru.lexender.project.storage.object.StorageObject;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class StorageObjectParser {
    private final File file;

    public StorageObjectParser(File file) {
        this.file = file;
    }

    public abstract @NonNull List<StorageObject<?>> parse() throws StorageTransformationException;

}
