package ru.lexender.project.server.storage.object;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Object that is allowed to be stored in classes that implement IStore interface.
 * @param <T> is a collection target class.
 * @see ru.lexender.project.server.storage.object.StorageInitializable
 */
@Getter @ToString
public class StorageObject<T extends Comparable<T> & StorageInitializable> implements Comparable<StorageObject<?>> {
    private static long ID = 0;
    @Expose private final long id;
    @Expose private final LocalDateTime creationDate;

    @Expose private T object;

    public static final StorageObject<?> nullObject = new StorageObject<>();

    public StorageObject(T object, boolean fromFile) {
        this.object = object;
        if (fromFile) {
            this.id = object.getId();
            ID = Math.max(ID, object.getId()+1);
            this.creationDate = object.getCreationDate();
        }
        else {
            this.id = ID++;
            this.creationDate = LocalDateTime.now();

        }
    }

    private StorageObject() {
        this.id = ID++;
        this.creationDate = LocalDateTime.now();
    }

    public void update(@NonNull StorageObject<?> object) throws ClassCastException {
        this.object = (T)(object.getObject());
    }

    public boolean isNull() {
        return (this == nullObject);
    }

    public int compareTo(StorageObject<?> obj) throws ClassCastException {
        return object.compareTo(((StorageObject<T>) obj).getObject());
    }



}

