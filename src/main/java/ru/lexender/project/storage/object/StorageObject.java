package ru.lexender.project.storage.object;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Object that is allowed to be stored in classes that implement IStore interface.
 * @param <T> is a collection target class.
 * @see ru.lexender.project.storage.object.StorageInitializable
 */
@Getter
public class StorageObject<T extends Comparable<T> & StorageInitializable> implements Comparable<StorageObject<?>> {
    private static long ID = 0;
    private final long id;
    private final LocalDateTime creationDate;
    private T object;

    public static final StorageObject<?> nullObject = new StorageObject<>();

    public StorageObject(T object) {
        this.object = object;
        this.id = (object.getId() != 0) ? object.getId() : ID++;
        this.creationDate = LocalDateTime.now();
        object.initialize(id, creationDate);
    }

    private StorageObject() {
        this.id = ID++;
        this.creationDate = LocalDateTime.now();
    }

    public void update(@NonNull StorageObject<?> object) throws ClassCastException {
        object.getObject().initialize(getId(), getCreationDate());
        this.object = (T)(object.getObject());
    }

    public static void initializeID(List<StorageObject<?>> objects) throws ClassCastException {
        for (StorageObject<?> object: objects) {
            ID = Math.max(object.getId(), ID);
        }
        ID++;
    }

    public boolean isNull() {
        return (this == nullObject);
    }

    @Override
    public String toString() {
        return object.toString();
    }

    public int compareTo(StorageObject<?> obj) throws ClassCastException {
        return object.compareTo(((StorageObject<T>) obj).getObject());
    }

}

