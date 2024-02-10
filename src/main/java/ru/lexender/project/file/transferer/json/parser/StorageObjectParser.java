package ru.lexender.project.file.transferer.json.parser;

import lombok.NonNull;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.storage.object.StorageObject;

import java.io.IOException;

public interface StorageObjectParser {
    public @NonNull StorageObject[] parse() throws StorageTransformationException;
}
