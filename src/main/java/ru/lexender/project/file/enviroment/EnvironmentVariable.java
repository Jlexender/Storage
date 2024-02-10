package ru.lexender.project.file.enviroment;

import lombok.Getter;

import java.io.File;

@Getter
public class EnvironmentVariable {
    private final File file;

    public EnvironmentVariable(String name) {
        this.file = new File(System.getenv(name));
    }
}
