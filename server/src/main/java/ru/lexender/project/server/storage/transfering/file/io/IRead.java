package ru.lexender.project.server.storage.transfering.file.io;

import java.io.File;
import java.io.IOException;

/**
 * File reader.
 */
public interface IRead {
    public String read(File file) throws IOException;
}
