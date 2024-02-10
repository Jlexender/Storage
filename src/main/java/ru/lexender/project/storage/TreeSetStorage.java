package ru.lexender.project.storage;

import lombok.Getter;
import lombok.NonNull;
import ru.lexender.project.storage.object.StorageObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

@Getter
public class TreeSetStorage implements IStore {

    private final TreeSet<StorageObject> collection;

    public TreeSetStorage() {
        collection = new TreeSet<>();
    }

    public void add(@NonNull StorageObject element) {
        collection.add(element);
    }

    public void clear() {
        collection.clear();
    }
}
