package ru.lexender.project.storage;

import ru.lexender.project.storage.object.StorageObject;

import java.util.Collection;

public interface IStore {
    public void add(StorageObject element);
    public Collection<StorageObject> getCollection();
    public void clear();
}
