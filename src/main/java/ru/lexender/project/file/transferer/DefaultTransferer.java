package ru.lexender.project.file.transferer;

import ru.lexender.project.exception.file.transferer.StorageIOException;
import ru.lexender.project.exception.file.transferer.StorageTransferException;
import ru.lexender.project.exception.file.transferer.StorageTransformationException;
import ru.lexender.project.file.FileSystem;
import ru.lexender.project.file.transferer.io.writer.IWrite;
import ru.lexender.project.file.transferer.io.writer.WriteViaPrintWriter;
import ru.lexender.project.file.transferer.json.parser.StorageObjectParser;
import ru.lexender.project.file.transferer.json.parser.StudyGroupParser;
import ru.lexender.project.file.transferer.json.serializer.GsonStorageSerializer;
import ru.lexender.project.file.transferer.json.serializer.ISerialize;
import ru.lexender.project.storage.IStore;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;

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
            StorageObjectParser parser = new StudyGroupParser(fileSystem.getFile());
            List<StorageObject<?>> data = parser.parse();

            for (StorageObject<?> object: data)
            {
                storage.add(object);
            }

            StorageObject.initializeID(data);

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
            exception.printStackTrace();
            throw new StorageTransferException(exception);
        } catch (StorageIOException exception) {
            exception.printStackTrace();
            throw new StorageTransferException("Loading storage from file failed");
        }
    }
}
