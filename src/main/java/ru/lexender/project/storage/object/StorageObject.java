package ru.lexender.project.storage.object;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StorageObject<T extends StorageInitializable> implements Comparable<StorageObject<T>> {
    private static long ID = 0;
    private final long id;
    private final LocalDateTime creationDate;
    private T object;

    public static final StorageObject<?> nullObject = new StorageObject<>() {};

    public StorageObject(T object) {
        this.object = object;
        this.id = ID++;
        this.creationDate = LocalDateTime.now();
        object.initialize(id, creationDate);
    }

    private StorageObject() {
        this.id = ID++;
        this.creationDate = LocalDateTime.now();
    }

    public void update(StorageObject<?> object) {
        if (object.getObject().getClass() == object.getClass())
            throw new IllegalArgumentException("Objects have different classes");
        this.object = (T)(object.getObject());
    }

    public static void initializeID(Object[] objects) throws ClassCastException {
        for (Object object: objects) {
            ID = Math.max(((StorageObject<?>)object).getId(), ID);
        }
    }

    public boolean isNull() {
        return (this == nullObject);
    }

    @Override
    public String toString() {
        return object.toString();
    }

    public int compareTo(StorageObject<T> object) {
        return Long.compare(this.getId(), object.getId());
    }
}

