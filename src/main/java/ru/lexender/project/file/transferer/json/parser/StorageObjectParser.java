package ru.lexender.project.file.transferer.json.parser;

import lombok.NonNull;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;

public interface StorageObjectParser {
    public @NonNull Object[] parse() throws StorageTransformationException;
}
