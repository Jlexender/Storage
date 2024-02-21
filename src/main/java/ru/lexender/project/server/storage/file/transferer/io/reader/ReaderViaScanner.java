package ru.lexender.project.server.storage.file.transferer.io.reader;

import ru.lexender.project.server.exception.file.transferer.StorageIOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ReaderViaScanner implements IRead {

    public String read(File file) throws IOException {
        Scanner reader = new Scanner(new InputStreamReader(new FileInputStream(file)));
        StringBuilder s = new StringBuilder();
        String line;

        while (reader.hasNext()) {
            line = reader.nextLine();
            s.append(line).append('\n');
        }
        return s.toString();
    }
}
