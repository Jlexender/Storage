package ru.lexender.project.server.storage.transfering.file;

import ru.lexender.project.server.exception.storage.file.transferer.StorageIOException;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransformationException;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.transfering.file.io.IWrite;
import ru.lexender.project.server.storage.transfering.file.io.Writer;
import ru.lexender.project.server.storage.transfering.file.json.parser.GsonStorageParser;
import ru.lexender.project.server.storage.transfering.file.json.serializer.GsonStorageSerializer;
import ru.lexender.project.server.storage.transfering.file.json.serializer.ISerialize;
import ru.lexender.project.server.storage.StorageObject;
import ru.lexender.project.server.storage.transfering.ITransfer;

import java.util.List;

public class FileTransferer implements ITransfer {
    FileBridge fileBridge;
    IStore storage;

    public FileTransferer(FileBridge fileBridge, IStore storage) {
        this.fileBridge = fileBridge;
        this.storage = storage;
    }

    public void transferIn() throws StorageTransferException {
        storage.clear();
        try {
            GsonStorageParser parser = new GsonStorageParser(fileBridge.getFile());
            List<StorageObject> data = parser.parse();

            for (StorageObject object: data) {
                storage.add(object);
            }

        } catch (Exception exception) {
            throw new StorageTransferException(exception);
        }
    }

    public void transferOut() throws StorageTransferException {
        try {
            IWrite writer = new Writer();
            ISerialize serializer = new GsonStorageSerializer(storage);
            writer.write(serializer.serialize(), fileBridge.getFile());
        } catch (StorageTransformationException exception) {
            throw new StorageTransferException(exception);
        } catch (StorageIOException exception) {
            throw new StorageTransferException("Serializing to file failed");
        }
    }
}
