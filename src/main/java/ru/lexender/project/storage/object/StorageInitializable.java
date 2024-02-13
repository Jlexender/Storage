package ru.lexender.project.storage.object;

import ru.lexender.project.description.Person;
import ru.lexender.project.description.Semester;

import java.time.LocalDateTime;

/**
 * Auxiliary interface for generalizing StorageObject elements.
 * @see ru.lexender.project.storage.object.StorageObject
 */
public interface StorageInitializable {
    public void initialize(long id, LocalDateTime creationDate);
    public long getId();
    public String getName();
    public Semester getSemesterEnum();
    public Person getGroupAdmin();
}
