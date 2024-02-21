package ru.lexender.project.server.storage.file.transferer.json.parser;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.server.exception.file.transferer.TransformationException;

import java.io.File;

@Getter
public abstract class Parser {
    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public abstract @NonNull Object parse() throws TransformationException;
}
