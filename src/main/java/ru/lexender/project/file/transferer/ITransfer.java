package ru.lexender.project.file.transferer;

import ru.lexender.project.exception.file.transferer.StorageTransferException;

public interface ITransfer {
    public void transferIn() throws StorageTransferException;
    public void transferOut() throws StorageTransferException;

}
