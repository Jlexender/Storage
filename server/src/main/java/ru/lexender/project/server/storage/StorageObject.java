package ru.lexender.project.server.storage;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import ru.lexender.project.server.storage.description.StudyGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Object that is allowed to be stored in classes that implement IStore interface.
 */
@Getter @ToString
public class StorageObject implements Comparable<StorageObject> {
    private static long ID = 0;
    @Expose private final long id;
    @Expose private final LocalDateTime creationDate;
    @ToString.Exclude private final boolean external;

    @Expose private StudyGroup object;

    public static final StorageObject nullObject = new StorageObject();

    public StorageObject(StudyGroup object, boolean external) {
        this.object = object;
        this.external = external;
        if (external) {
            this.id = object.getId();
            ID = Math.max(ID, object.getId()+1);
            this.creationDate = object.getCreationDate();
        }
        else {
            this.id = ID++;
            this.creationDate = LocalDateTime.now();
        }
    }

    public StorageObject(ResultSet resultSet) throws SQLException, IllegalAccessException {
        this.id = Long.parseLong(resultSet.getString("id"));
        this.creationDate = LocalDateTime.parse(resultSet.getString("creationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        this.object = new StudyGroup(resultSet);
        this.external = true;
        ID = Math.max(ID, id) + 1;
    }

    private StorageObject() {
        this.id = ID++;
        this.creationDate = LocalDateTime.now();
        this.external = false;
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

