package ru.lexender.project.file.transferer.io.writer;

import ru.lexender.project.exception.file.transferer.StorageIOException;

import java.io.File;
import java.io.PrintWriter;

public class WriteViaPrintWriter implements IWrite {
    private final File file;
    public WriteViaPrintWriter(File file) {
        this.file = file;
    }
    public void write(String data) throws StorageIOException {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.write(data);
            writer.close();
        } catch (Exception exception) {
            throw new StorageIOException(exception);
        }
    }
}

