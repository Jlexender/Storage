package ru.lexender.project.storage;

import ru.lexender.project.storage.object.StorageObject;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface IStore {
    public void add(StorageObject element);
    public Collection<StorageObject> getCollectionCopy();
    public void update(long objectId, StorageObject object) throws IllegalAccessException;
    public void clear();
    public StorageObject getById(long objectId) throws NoSuchElementException;
    public boolean remove(StorageObject object);
}
