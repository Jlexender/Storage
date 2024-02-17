package ru.lexender.project.server.file.transferer.io.writer;

import ru.lexender.project.server.exception.file.transferer.StorageIOException;

/**
 * Data writer.
 */
public interface IWrite {
    public void write(String data) throws StorageIOException;
}
