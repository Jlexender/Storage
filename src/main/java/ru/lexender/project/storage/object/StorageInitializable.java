package ru.lexender.project.storage.object;

import java.time.LocalDateTime;

public interface StorageInitializable {
    public void initialize(long id, LocalDateTime creationDate);
}