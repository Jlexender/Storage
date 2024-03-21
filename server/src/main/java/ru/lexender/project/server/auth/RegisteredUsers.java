package ru.lexender.project.server.auth;

import ru.lexender.project.server.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RegisteredUsers {
    private final String address;

    public RegisteredUsers(String databaseName, String port) {
        address = String.format("jdbc:postgresql://localhost:%s/%s", port, databaseName);
    }

    public boolean isConnectable() {
        try (Connection connection = DriverManager.getConnection(address, "alex", "0000")) {
            if (!connection.getMetaData().getTables(null, null, "users", null).next()) {
                Server.logger.info("No 'users' table found: trying to create a table");

                String tableQuery = """
                        CREATE TABLE users(
                            uid serial PRIMARY KEY,
                            username varchar(20) UNIQUE NOT NULL,
                            hash char(256) NOT NULL,
                            salt varchar(20) NOT NULL
                        );
                        """;

                connection.createStatement().executeUpdate(tableQuery);
                Server.logger.info("OK created table 'users'");
            }
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }
}
