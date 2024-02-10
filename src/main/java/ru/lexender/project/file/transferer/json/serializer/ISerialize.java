package ru.lexender.project.file.transferer.json.serializer;

import ru.lexender.project.exception.file.transferer.StorageTransformationException;

import java.io.IOException;

public interface ISerialize {
    public String serialize() throws StorageTransformationException;
}
