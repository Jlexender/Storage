package ru.lexender.project.storage;

import lombok.NonNull;
import ru.lexender.project.storage.object.StorageObject;

import java.util.NoSuchElementException;
import java.util.TreeSet;

public class TreeSetStorage implements IStore {
    private final TreeSet<StorageObject<?>> collection;

    public TreeSetStorage() {
        collection = new TreeSet<>();
    }

    public void add(@NonNull StorageObject<?> element) {
        collection.add(element);
    }

    public void clear() {
        collection.clear();
    }

    public StorageObject<?> getById(long id) throws NoSuchElementException {
        for (StorageObject<?> object: collection) {
            if (object.getId() == id) return object;
        }
        throw new NoSuchElementException("No such element");
    }


    public boolean remove(StorageObject<?> object) {
        if (collection.contains(object)) {
            collection.remove(object);
            return true;
        }
        return false;
    }

    public TreeSet<StorageObject<?>> getCollectionCopy() {
        TreeSet<StorageObject<?>> dataCopy = new TreeSet<>();
        dataCopy.addAll(collection);
        return dataCopy;
    }
}
