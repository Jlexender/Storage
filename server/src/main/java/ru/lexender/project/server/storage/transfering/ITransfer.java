package ru.lexender.project.server.storage.transfering;

import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;

/**
 * Transfers data from file and storage in both ways.
 */
public interface ITransfer {
    public void transferIn() throws StorageTransferException;
    public void transferOut() throws StorageTransferException;

}
