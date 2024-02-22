package ru.lexender.project.server.storage.file.transferer;

import ru.lexender.project.server.exception.storage.file.transferer.StorageIOException;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransformationException;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.file.FileSystem;
import ru.lexender.project.server.storage.file.transferer.io.writer.IWrite;
import ru.lexender.project.server.storage.file.transferer.io.writer.WriteViaPrintWriter;
import ru.lexender.project.server.storage.file.transferer.json.parser.GsonStorageParser;
import ru.lexender.project.server.storage.file.transferer.json.serializer.GsonStorageSerializer;
import ru.lexender.project.server.storage.file.transferer.json.serializer.ISerialize;
import ru.lexender.project.server.storage.object.StorageObject;

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
            GsonStorageParser parser = new GsonStorageParser(fileSystem.getFile());
            List<StorageObject> data = parser.parse();

            for (StorageObject object: data) {
                storage.add(object);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new StorageTransferException(exception);
        }
    }

    public void transferOut() throws StorageTransferException {
        try {
            IWrite writer = new WriteViaPrintWriter();
            ISerialize serializer = new GsonStorageSerializer(storage);
            writer.write(serializer.serialize(), fileSystem.getFile());
        } catch (StorageTransformationException exception) {
            throw new StorageTransferException(exception);
        } catch (StorageIOException exception) {
            throw new StorageTransferException("Loading storage from file failed");
        }
    }
}
