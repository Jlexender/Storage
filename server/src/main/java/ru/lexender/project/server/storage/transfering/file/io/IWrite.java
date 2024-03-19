package ru.lexender.project.server.storage.transfering.file.io;

import ru.lexender.project.server.exception.storage.file.transferer.StorageIOException;

import java.io.File;

/**
 * Data writer.
 */
public interface IWrite {
    public void write(String data, File file) throws StorageIOException;
}
