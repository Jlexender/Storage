package ru.lexender.project.console.handler.builder;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.description.Color;
import ru.lexender.project.description.Coordinates;
import ru.lexender.project.description.Country;
import ru.lexender.project.description.FormOfEducation;
import ru.lexender.project.description.Person;
import ru.lexender.project.description.Semester;
import ru.lexender.project.description.StudyGroup;
import ru.lexender.project.exception.console.handler.StorageObjectBuilderException;
import ru.lexender.project.storage.object.StorageObject;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class StorageObjectBuilder {
    private final int argumentsAmount;
    private final String[] orderedFields;

    public StorageObjectBuilder(int argumentsAmount, @NonNull String[] orderedFields) {
        this.argumentsAmount = argumentsAmount;
        this.orderedFields = orderedFields;
    }

    public abstract Optional<StorageObject> build(List<String> arguments) throws StorageObjectBuilderException;
}
