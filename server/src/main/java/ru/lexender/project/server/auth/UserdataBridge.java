package ru.lexender.project.server.auth;

import lombok.Getter;
import ru.lexender.project.server.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class UserdataBridge {
    private final String address, username, password;

    public UserdataBridge(String databaseName, int port, String username, String password) {
        address = String.format("jdbc:postgresql://localhost:%d/%s", port, databaseName);
        this.username = username;
        this.password = password;
    }

    public boolean isConnectable() {
        try (Connection connection = DriverManager.getConnection(address, "alex", "0000")) {
            if (!connection.getMetaData().getTables(null, null, "users", null).next()) {
                Server.logger.info("No 'users' table found: trying to create a table");

                String tableQuery = """
                        CREATE TABLE users(
                            uid serial PRIMARY KEY,
                            username varchar(40) UNIQUE NOT NULL,
                            hash char(64) NOT NULL,
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
