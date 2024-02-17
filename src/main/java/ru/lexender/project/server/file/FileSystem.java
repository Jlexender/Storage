package ru.lexender.project.server.file;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.server.file.variable.EnvironmentVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Provides information about file and its last modification date.
 */
@Getter
public class FileSystem {
    private final @NonNull File file;
    private final @NonNull LocalDateTime modificationDate;

    public FileSystem(File file) {
        this.file = file;

        LocalDateTime creationTime;
        try {
            FileTime creationTimeAttribute = Files.getLastModifiedTime(file.toPath());
            creationTime = LocalDateTime.ofInstant(creationTimeAttribute.toInstant(), ZoneId.systemDefault());
        } catch (IOException exception) {
            creationTime = LocalDateTime.now();
        }
        modificationDate = creationTime;
    }

    public FileSystem(EnvironmentVariable variable) {
        this(variable.getFile());
    }
}
