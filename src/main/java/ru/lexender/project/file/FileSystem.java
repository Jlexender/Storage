package ru.lexender.project.file;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.file.enviroment.EnvironmentVariable;

import java.io.File;

@Getter
public class FileSystem {
    private final @NonNull File file;

    public FileSystem(File file) {
        this.file = file;
    }

    public FileSystem(EnvironmentVariable variable) {
        this(variable.getFile());
    }
}
