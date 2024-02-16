package ru.lexender.project.file.transferer;

import com.google.gson.reflect.TypeToken;
import ru.lexender.project.description.StudyGroup;
import ru.lexender.project.exception.file.transferer.StorageIOException;
import ru.lexender.project.exception.file.transferer.StorageTransferException;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.file.FileSystem;
import ru.lexender.project.file.transferer.io.writer.IWrite;
import ru.lexender.project.file.transferer.io.writer.WriteViaPrintWriter;
import ru.lexender.project.file.transferer.json.parser.GsonStorageParser;
import ru.lexender.project.file.transferer.json.serializer.GsonStorageSerializer;
import ru.lexender.project.file.transferer.json.serializer.ISerialize;
import ru.lexender.project.storage.IStore;
import ru.lexender.project.storage.object.StorageObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * The transferer provided with ITransfer interface.
 * @see ru.lexender.project.file.transferer.ITransfer
 */
public class DefaultTransferer implements ITransfer {
    FileSystem fileSystem;
    IStore storage;

    public DefaultTransferer(FileSystem fileSystem, IStore storage) {
        this.fileSystem = fileSystem;
        this.storage = storage;
    }

    public void transferIn() throws StorageTransferException {
        storage.clear();
        try {
            Type studyGroupType = TypeToken.getParameterized(StorageObject.class, StudyGroup.class).getType();
            GsonStorageParser parser = new GsonStorageParser(fileSystem.getFile(), studyGroupType);

            List<StorageObject<?>> data = parser.parse();

            for (StorageObject<?> object: data)
            {
                storage.add(object);
            }

        } catch (Exception exception) {
            throw new StorageTransferException(exception);
        }
    }

    public void transferOut() throws StorageTransferException {
        try {
            IWrite writer = new WriteViaPrintWriter(fileSystem.getFile());
            ISerialize serializer = new GsonStorageSerializer(storage);
            writer.write(serializer.serialize());
        } catch (StorageTransformationException exception) {
            throw new StorageTransferException(exception);
        } catch (StorageIOException exception) {
            throw new StorageTransferException("Loading storage from file failed");
        }
    }
}
