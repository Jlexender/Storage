package ru.lexender.project.storage.object;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class StorageObject implements Comparable<StorageObject> {
    private static long ID = 0;
    private final long id;
    private final LocalDateTime creationDate;

    public StorageObject() {
        this.id = ++ID;
        this.creationDate = LocalDateTime.now();
    }

    public static void initializeID(StorageObject[] objects) {
        for (StorageObject object: objects) {
            ID = Math.max(object.getId(), ID);
        }
    }

    public int compareTo(StorageObject object) {
        return Long.compare(this.getId(), object.getId());
    }
}

