package ru.lexender.project.file.transferer.io.reader;

import ru.lexender.project.exception.file.transferer.StorageIOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ReaderViaScanner implements IRead {
    private final InputStream inputStream;

    public ReaderViaScanner(File file) throws StorageIOException {
        try {
            this.inputStream = new FileInputStream(file);
        } catch (IOException exception) {
            throw new StorageIOException("Unable to read collection from file");
        }
    }

    public ReaderViaScanner(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String read() {
        Scanner reader = new Scanner(new InputStreamReader(inputStream));
        StringBuilder s = new StringBuilder();
        String line;

        while (reader.hasNext()) {
            line = reader.nextLine();
            s.append(line);
        }
        return s.toString();
    }
}
