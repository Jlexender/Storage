package ru.lexender.project.server.storage;

import ru.lexender.project.server.storage.object.StorageObject;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Stores the collection's elements.
 */
public interface IStore {
    public void add(StorageObject element);
    public Collection<StorageObject> getCollectionCopy();
    public void clear();
    public StorageObject getById(long objectId) throws NoSuchElementException;
    public boolean remove(StorageObject object);
    public StorageObject getMin();
    public int size();
}
