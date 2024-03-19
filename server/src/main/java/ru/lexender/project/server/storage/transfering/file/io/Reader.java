package ru.lexender.project.server.storage.transfering.file.io;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class Reader implements IRead {

    public String read(File file) throws IOException {
        try (FileChannel channel = FileChannel.open(file.toPath())) {
            StringBuilder stringBuilder = new StringBuilder();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (channel.read(buffer) != -1) {
                buffer.flip();
                stringBuilder.append(StandardCharsets.UTF_8.decode(buffer));
                buffer.clear();
            }

            return stringBuilder.toString();
        }
    }
}
