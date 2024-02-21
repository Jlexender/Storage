package ru.lexender.project.server.storage.file.transferer.json.serializer;

import ru.lexender.project.server.exception.storage.file.transferer.StorageTransformationException;

/**
 * Serializes the specified object.
 */
public interface ISerialize {
    public String serialize() throws StorageTransformationException;
}
