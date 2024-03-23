package ru.lexender.project.server.storage;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeSet;

/**
 * Class provided for storing elements.
 * @see ru.lexender.project.server.storage.IStore
 */
public class Storage implements IStore {
    private final ArrayList<StorageObject> collection;

    public Storage() {
        collection = new ArrayList<>();
    }

    public void add(@NonNull StorageObject element) throws ClassCastException {
        collection.add(element);
    }

    public void clear() {
        collection.clear();
    }

    public StorageObject getById(long id) throws NoSuchElementException {
        if (collection.stream().anyMatch(o -> o.getId() == id))
            return collection.stream().filter(o -> o.getId() == id).toList().get(0);
        throw new NoSuchElementException("No such element");
    }

    public int size() {
        return collection.size();
    }

    public boolean remove(StorageObject object) {
        return collection.remove(object);
    }

    public boolean removeAll(Collection<StorageObject> collection) {
        return this.collection.removeAll(collection);
    }

    public StorageObject getMin() throws NoSuchElementException {
        Optional<StorageObject> object = collection.stream().min(Comparator.naturalOrder());
        if (object.isEmpty()) throw new NoSuchElementException("Collection is empty, no min element provided");
        return object.get();
    }

    public TreeSet<StorageObject> getCollectionCopy() {
        TreeSet<StorageObject> dataCopy = new TreeSet<>();
        dataCopy.addAll(collection);
        return dataCopy;
    }
}
