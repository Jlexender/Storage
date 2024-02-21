package ru.lexender.project.server.storage.file.transferer;

import ru.lexender.project.server.exception.file.transferer.StorageTransferException;

/**
 * Transfers data from file and storage in both ways.
 */
public interface ITransfer {
    public void transferIn() throws StorageTransferException;
    public void transferOut() throws StorageTransferException;

}
