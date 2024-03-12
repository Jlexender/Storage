package ru.lexender.project.server.storage.file.transferer.io.reader;

import java.io.File;
import java.io.IOException;

/**
 * File reader.
 */
public interface IRead {
    public String read(File file) throws IOException;
}
