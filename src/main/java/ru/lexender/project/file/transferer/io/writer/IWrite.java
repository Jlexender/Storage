package ru.lexender.project.file.transferer.io.writer;

import ru.lexender.project.exception.file.transferer.StorageIOException;

/**
 * Data writer.
 */
public interface IWrite {
    public void write(String data) throws StorageIOException;
}
