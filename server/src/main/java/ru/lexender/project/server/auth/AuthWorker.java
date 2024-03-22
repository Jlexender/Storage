package ru.lexender.project.server.auth;

import com.google.common.hash.Hashing;
import lombok.Getter;
import ru.lexender.project.inbetween.Userdata;
import ru.lexender.project.server.Server;
import ru.lexender.project.server.exception.server.auth.UserdataNotConnectedException;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

@Getter
public class AuthWorker {
    private final UserdataBridge userdataBridge;
    private static final String pepper = "A3P)+g";

    private static String getSalt() {
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 12; ++i) {
            salt.append((char)random.nextInt(33, 93));
        }
        return salt.toString();
    }

    public AuthWorker(UserdataBridge userdataBridge) {
        this.userdataBridge = userdataBridge;
    }

    public boolean isValid(Userdata userdata) throws UserdataNotConnectedException {
        if (!userdataBridge.isConnectable())
            throw new UserdataNotConnectedException("Userdata is not connected");

        String salt = getSalt();
        String hash = Hashing.sha256()
                .hashString(pepper+userdata.getPassword()+salt, StandardCharsets.UTF_8)
                .toString();

        String insertStatement = "INSERT INTO users(username, hash, salt) VALUES (?,?,?)";
        String existsStatement = "SELECT uid FROM users where username = ?";
        try (Connection connection = DriverManager.getConnection(userdataBridge.getAddress(),
                userdataBridge.getUsername(), userdataBridge.getPassword());
             PreparedStatement statement = connection.prepareStatement(insertStatement);
             PreparedStatement statement2 = connection.prepareStatement(existsStatement)) {

            statement2.setString(1, userdata.getUsername());
            ResultSet resultSet = statement2.executeQuery();
            if (resultSet.next()) {
                Server.logger.warn("Unable to register {}: user already exists", userdata);
                return isPasswordCorrect(userdata);
            }

            statement.setString(1, userdata.getUsername());
            statement.setString(2, hash);
            statement.setString(3, salt);

            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            Server.logger.error("Unable to register {}: {}", userdata, exception.getMessage());
        }
        return false;
    }

    public boolean isPasswordCorrect(Userdata userdata) throws UserdataNotConnectedException {
        if (!userdataBridge.isConnectable())
            throw new UserdataNotConnectedException("Userdata is not connected");

        String selectStatement = "SELECT salt, hash FROM users WHERE username=?";
        try (Connection connection = DriverManager.getConnection(userdataBridge.getAddress(),
                userdataBridge.getUsername(), userdataBridge.getPassword());
             PreparedStatement statement = connection.prepareStatement(selectStatement)) {

            statement.setString(1, userdata.getUsername());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String salt = resultSet.getString(1);
                String hash = Hashing.sha256()
                        .hashString(pepper+userdata.getPassword()+salt, StandardCharsets.UTF_8)
                        .toString();

                return hash.equals(resultSet.getString(2));
            }
        } catch (SQLException exception) {
            Server.logger.error("Unable to register {}: {}", userdata, exception.getMessage());
        }
        return false;
    }


}
