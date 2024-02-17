package ru.lexender.project.server.storage.object;

import ru.lexender.project.server.description.Person;
import ru.lexender.project.server.description.Semester;

import java.time.LocalDateTime;

/**
 * Auxiliary interface for generalizing StorageObject elements.
 * @see ru.lexender.project.server.storage.object.StorageObject
 */
public interface StorageInitializable {
    public long getId();
    public String getName();
    public LocalDateTime getCreationDate();
    public Semester getSemesterEnum();
    public Person getGroupAdmin();
}
