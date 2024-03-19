package ru.lexender.project.server.storage.transfering.db;

import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;
import ru.lexender.project.server.storage.transfering.ITransfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresQLTransferer implements ITransfer {
    private final String address;

    public PostgresQLTransferer(String databaseName, String port) {
        address = String.format("jdbc:postgresql://localhost:%s/%s", databaseName, port);
    }

    public void transferIn() throws StorageTransferException {
        try (Connection connection = DriverManager.getConnection(address)) {
            System.out.println("SUCCESS");
        } catch (SQLException exception) {
            throw new StorageTransferException("Can't parse from database");
        }
    }

    public void transferOut() throws StorageTransferException {

    }
}
