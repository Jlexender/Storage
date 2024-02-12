package ru.lexender.project.file;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.file.variable.EnvironmentVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class FileSystem {
    private final @NonNull File file;
    private final @NonNull LocalDateTime creationDate;

    public FileSystem(File file) {
        this.file = file;

        LocalDateTime creationTime;
        try {
            FileTime creationTimeAttribute = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
            creationTime = LocalDateTime.ofInstant(creationTimeAttribute.toInstant(), ZoneId.systemDefault());
        } catch (IOException exception) {
            creationTime = LocalDateTime.now();
        }
        creationDate = creationTime;
    }

    public FileSystem(EnvironmentVariable variable) {
        this(variable.getFile());
    }
}
