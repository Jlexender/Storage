package ru.lexender.project.storage;

import lombok.NonNull;
import ru.lexender.project.storage.object.StorageObject;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeSet;

public class TreeSetStorage implements IStore {
    private final TreeSet<StorageObject<?>> collection;

    public TreeSetStorage() {
        collection = new TreeSet<>();
    }

    public void add(@NonNull StorageObject<?> element) throws ClassCastException {
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

    public int size() {
        return collection.size();
    }

    public boolean remove(StorageObject<?> object) {
        if (collection.contains(object)) {
            collection.remove(object);
            return true;
        }
        return false;
    }

    public StorageObject<?> getMin() throws NoSuchElementException {
        Optional<StorageObject<?>> object = collection.stream().min(Comparator.naturalOrder());
        if (object.isEmpty()) throw new NoSuchElementException("Collection is empty, no min element provided");
        return object.get();
    }

    public TreeSet<StorageObject<?>> getCollectionCopy() {
        TreeSet<StorageObject<?>> dataCopy = new TreeSet<>();
        dataCopy.addAll(collection);
        return dataCopy;
    }
}
