package ru.lexender.project.file.variable;

import lombok.Getter;

import java.io.File;

/**
 * Class that stores information about file that is defined from environment variable.
 */
@Getter
public class EnvironmentVariable {
    private final File file;

    public EnvironmentVariable(String name) {
        this.file = new File(System.getenv(name));
    }
}
