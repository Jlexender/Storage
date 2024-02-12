package ru.lexender.project.storage.object;

import java.time.LocalDateTime;

/**
 * Auxiliary interface for generalizing StorageObject elements.
 * @see ru.lexender.project.storage.object.StorageObject
 */
public interface StorageInitializable {
    public void initialize(long id, LocalDateTime creationDate);
    public long getId();
    public String getName();
}
