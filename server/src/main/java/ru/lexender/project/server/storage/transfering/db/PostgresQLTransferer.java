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
    private final String address;
    private final IStore storage;

    public PostgresQLTransferer(String databaseName, String port, IStore storage) {
        address = String.format("jdbc:postgresql://localhost:%s/%s", port, databaseName);
        this.storage = storage;
    }

    public void transferIn() throws StorageTransferException {
        try (Connection connection = DriverManager.getConnection(address, "alex", "0000");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DATA");
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
        String SQLString = "INSERT INTO data(" +
                "name, " +
                "coordinates_x, " +
                "coordinates_y, " +
                "creationdate, " +
                "studentscount, " +
                "averagemark, " +
                "formofeducation, " +
                "semesterenum, " +
                "admin_name, " +
                "admin_weight, " +
                "admin_eyecolor, " +
                "admin_haircolor, " +
                "admin_nationality" +
                ") VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(address, "alex", "0000");
             PreparedStatement statement = connection.prepareStatement(SQLString)) {
             for (StorageObject object: storage.getCollectionCopy()) {
                 if (object.isExternal()) continue;

                 statement.setString(1, object.getObject().getName());
                 statement.setLong(2, object.getObject().getCoordinates().getX());
                 statement.setLong(3, object.getObject().getCoordinates().getY());
                 statement.setString(4, object.getCreationDate().format(
                         DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"))
                 );
                 statement.setLong(5, object.getObject().getStudentsCount());
                 statement.setLong(6, object.getObject().getAverageMark());
                 statement.setString(7, object.getObject().getFormOfEducation().toString());
                 statement.setString(8, object.getObject().getSemesterEnum().toString());
                 statement.setString(9, object.getObject().getGroupAdmin().getName());
                 statement.setInt(10, object.getObject().getGroupAdmin().getWeight());
                 statement.setString(11, object.getObject().getGroupAdmin().getEyeColor().toString());
                 statement.setString(12, object.getObject().getGroupAdmin().getHairColor().toString());
                 statement.setString(13, object.getObject().getGroupAdmin().getNationality().toString());

                 statement.addBatch();
             }

             statement.executeBatch();
        } catch (SQLException exception) {
            throw new StorageTransferException("Can't parse from database");
        }
    }
}
