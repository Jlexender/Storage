package ru.lexender.project.file.transferer.json.parser;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.storage.object.StorageObject;

import java.io.File;
import java.util.List;

@Getter
public abstract class StorageObjectParser {
    private final File file;

    public StorageObjectParser(File file) {
        this.file = file;
    }

    public abstract @NonNull List<StorageObject<?>> parse() throws StorageTransformationException;

}
