package ru.lexender.project.file.transferer.json.parser;

import lombok.NonNull;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

public interface StorageObjectParser {
    public @NonNull List<StorageObject<?>> parse() throws StorageTransformationException;
}
