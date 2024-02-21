package ru.lexender.project.server.storage.file.transferer.io.writer;

import ru.lexender.project.server.exception.file.transferer.StorageIOException;

import java.io.File;
import java.io.PrintWriter;

public class WriteViaPrintWriter implements IWrite {
    public void write(String data, File file) throws StorageIOException {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.write(data);
            writer.close();
        } catch (Exception exception) {
            throw new StorageIOException(exception);
        }
    }
}

