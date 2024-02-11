package ru.lexender.project.storage.object;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class StorageObject implements Comparable<StorageObject> {
    private static long ID = 0;
    private final long id;
    private final LocalDateTime creationDate;

    public static final StorageObject nullObject = new StorageObject() {
        public void update(Object[] orderedFields) {}

        public Object[] getOrderedFields() {
            return new Object[0];
        }
    };

    public StorageObject() {
        this.id = ID++;
        this.creationDate = LocalDateTime.now();
    }

    public abstract Object[] getOrderedFields();

    public static void initializeID(StorageObject[] objects) {
        for (StorageObject object: objects) {
            ID = Math.max(object.getId(), ID);
        }
        ID++;
    }

    public abstract void update(Object[] orderedFields) throws IllegalAccessException;

    public boolean isNull() {
        return (this == nullObject);
    }

    public int compareTo(StorageObject object) {
        return Long.compare(this.getId(), object.getId());
    }
}

