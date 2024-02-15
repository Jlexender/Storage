package ru.lexender.project.file.transferer.json.parser;

import lombok.NonNull;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.storage.object.StorageObject;

import java.io.File;
import java.util.List;

/**
 * Parses data from file to a list of StorageObject classes.
 * @see ru.lexender.project.storage.object.StorageObject
 */

public abstract class StorageObjectParser extends Parser {

    public StorageObjectParser(File file) {
        super(file);
    }

    public abstract @NonNull List<StorageObject<?>> parse() throws StorageTransformationException;

}
