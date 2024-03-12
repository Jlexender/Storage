package ru.lexender.project.server.storage.file.transferer.io.writer;

import ru.lexender.project.server.exception.storage.file.transferer.StorageIOException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

public class WriteViaPrintWriter implements IWrite {
    public void write(String data, File file) throws StorageIOException {

        try (FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.WRITE)) {
            new PrintWriter(file).close();
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(data);
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
        } catch (IOException exception) {
            throw new StorageIOException(exception);
        }
    }
}
