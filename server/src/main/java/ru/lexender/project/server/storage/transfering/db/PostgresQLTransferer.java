package ru.lexender.project.server.storage.transfering.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lexender.project.server.exception.storage.file.transferer.StorageTransferException;
import ru.lexender.project.server.storage.IStore;
import ru.lexender.project.server.storage.StorageObject;
import ru.lexender.project.server.storage.transfering.ITransfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class PostgresQLTransferer implements ITransfer {
    public static final Logger logger = LoggerFactory.getLogger("Transferer");
    private final String address, username, password;
    private final IStore storage;

    public PostgresQLTransferer(String databaseName, int port, IStore storage, String username, String password) {
        address = String.format("jdbc:postgresql://localhost:%d/%s", port, databaseName);
        this.storage = storage;
        this.username = username;
        this.password = password;
    }

    private void checkTable(Connection connection) throws SQLException {
        if (!connection.getMetaData().getTables(null, null, "data", null).next()) {
            logger.info("No 'data' table found: trying to create a table");

            String tableQuery = """
                        CREATE TABLE data(
                            id bigserial,
                            author varchar(20),
                            name varchar(200),
                            coordinates_x bigint,
                            coordinates_y bigint,
                            creationDate varchar(30),
                            studentsCount bigint,
                            averageMark bigint,
                            formOfEducation varchar(30),
                            semesterEnum varchar(30),
                            admin_name varchar(200),
                            admin_weight integer,
                            admin_eyeColor varchar(30),
                            admin_hairColor varchar(30),
                            admin_nationality varchar(30)
                        );
                        """;

            connection.createStatement().executeUpdate(tableQuery);
            logger.info("OK created table 'data'");
        }
    }


    public void transferIn() throws StorageTransferException {
        try (Connection connection = DriverManager.getConnection(address, username, password);
             Statement statement = connection.createStatement()) {
            logger.debug("Driver tries to establish the connection {}", address);

            checkTable(connection);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM data");
            while (resultSet.next()) {
                try {
                    storage.add(new StorageObject(resultSet));
                } catch (IllegalAccessException exception) {
                    logger.warn("Unable to get object from DB, skipping");
                }
            }
            resultSet.close();
        } catch (SQLException exception) {
            throw new StorageTransferException("Can't parse from database");
        }
    }

    public void transferOut() throws StorageTransferException {
        String SQLString = """
                INSERT INTO data(
                name,
                author,
                coordinates_x,
                coordinates_y,
                creationdate,
                studentscount,
                averagemark,
                formofeducation,
                semesterenum,
                admin_name,
                admin_weight,
                admin_eyecolor,
                admin_haircolor,
                admin_nationality
                ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);
            """;

        try (Connection connection = DriverManager.getConnection(address, username, password);
             PreparedStatement statement = connection.prepareStatement(SQLString)) {
            logger.debug("Driver tries to establish the connection");

            checkTable(connection);
            connection.createStatement().executeUpdate("TRUNCATE TABLE data");

            for (StorageObject object: storage.getCollectionCopy()) {
                 statement.setString(1, object.getObject().getName());
                 statement.setString(2, object.getAuthor());
                 statement.setLong(3, object.getObject().getCoordinates().getX());
                 statement.setLong(4, object.getObject().getCoordinates().getY());
                 statement.setString(5, object.getCreationDate().format(DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss")));
                 statement.setLong(6, object.getObject().getStudentsCount());
                 statement.setLong(7, object.getObject().getAverageMark());
                 statement.setString(8, object.getObject().getFormOfEducation().toString());
                 statement.setString(9, object.getObject().getSemesterEnum().toString());
                 statement.setString(10, object.getObject().getGroupAdmin().getName());
                 statement.setInt(11, object.getObject().getGroupAdmin().getWeight());
                 statement.setString(12, object.getObject().getGroupAdmin().getEyeColor().toString());
                 statement.setString(13, object.getObject().getGroupAdmin().getHairColor().toString());
                 statement.setString(14, object.getObject().getGroupAdmin().getNationality().toString());

                 statement.addBatch();
             }

            statement.executeBatch();
        } catch (SQLException exception) {
            logger.error("Storing to DB FAILED");
            throw new StorageTransferException("Can't store to the database");
        }
    }
}
