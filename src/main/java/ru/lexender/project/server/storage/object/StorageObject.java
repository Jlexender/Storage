package ru.lexender.project.server.storage.object;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import ru.lexender.project.server.storage.description.StudyGroup;

import java.time.LocalDateTime;

/**
 * Object that is allowed to be stored in classes that implement IStore interface.
 */
@Getter @ToString
public class StorageObject implements Comparable<StorageObject> {
    private static long ID = 0;
    @Expose private final long id;
    @Expose private final LocalDateTime creationDate;

    @Expose private StudyGroup object;

    public static final StorageObject nullObject = new StorageObject();

    public StorageObject(StudyGroup object, boolean fromFile) {
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

    public void update(@NonNull StorageObject object) {
        this.object = object.getObject();
    }

    public boolean isNull() {
        return (this == nullObject);
    }

    public int compareTo(StorageObject obj) {
        return object.compareTo(obj.getObject());
    }



}

