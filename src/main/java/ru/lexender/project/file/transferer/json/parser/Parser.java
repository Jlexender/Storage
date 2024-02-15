package ru.lexender.project.file.transferer.json.parser;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.exception.file.transferer.TransformationException;

import java.io.File;

@Getter
public abstract class Parser {
    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public abstract @NonNull Object parse() throws TransformationException;
}
