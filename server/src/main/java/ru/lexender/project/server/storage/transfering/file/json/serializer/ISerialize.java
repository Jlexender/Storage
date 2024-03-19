package ru.lexender.project.server.storage.transfering.file.json.serializer;

import ru.lexender.project.server.exception.storage.file.transferer.StorageTransformationException;

/**
 * Serializes the specified object.
 */
public interface ISerialize {
    public String serialize() throws StorageTransformationException;
}
