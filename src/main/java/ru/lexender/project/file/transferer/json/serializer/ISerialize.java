package ru.lexender.project.file.transferer.json.serializer;

import ru.lexender.project.exception.file.transferer.StorageTransformationException;

/**
 * Serializes the specified object.
 */
public interface ISerialize {
    public String serialize() throws StorageTransformationException;
}
