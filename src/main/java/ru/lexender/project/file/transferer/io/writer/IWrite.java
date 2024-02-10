package ru.lexender.project.file.transferer.io.writer;

import ru.lexender.project.exception.file.transferer.StorageIOException;

public interface IWrite {
    public void write(String data) throws StorageIOException;
}
