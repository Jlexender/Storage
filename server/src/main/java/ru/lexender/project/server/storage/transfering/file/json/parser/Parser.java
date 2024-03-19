package ru.lexender.project.server.storage.transfering.file.json.parser;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.server.exception.storage.file.transferer.TransformationException;

import java.io.File;

@Getter
public abstract class Parser {
    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public abstract @NonNull Object parse() throws TransformationException;
}
