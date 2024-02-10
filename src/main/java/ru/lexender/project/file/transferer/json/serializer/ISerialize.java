package ru.lexender.project.file.transferer.json.serializer;

import ru.lexender.project.exception.file.transferer.StorageTransformationException;

public interface ISerialize {
    public String serialize() throws StorageTransformationException;
}
