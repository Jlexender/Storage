package ru.lexender.project.server.auth;

import lombok.Getter;
import ru.lexender.project.server.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class UserdataBridge {
    private final String address, username, password, schemaName;

    public UserdataBridge(String databaseHost, String databaseName, String schemaName, int port, String username, String password) {
        address = String.format("jdbc:postgresql://%s:%d/%s", databaseHost, port, databaseName);
        this.username = username;
        this.password = password;
        this.schemaName = schemaName;
    }

    public boolean isConnectable() {
        try (Connection connection = DriverManager.getConnection(address, username, password)) {

            if (!connection.getMetaData().getTables(null, null, "storage_users", null).next()) {
                Server.logger.info("No 'storage_users' table found: trying to create a table");

                String tableQuery = """
                        CREATE TABLE storage_users(
                            uid serial PRIMARY KEY,
                            username varchar(40) UNIQUE NOT NULL,
                            hash char(64) NOT NULL,
                            salt varchar(20) NOT NULL
                        );
                        """;

                connection.createStatement().executeUpdate(tableQuery);
                Server.logger.info("OK created table 'storage_users'");
            }
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }
}
