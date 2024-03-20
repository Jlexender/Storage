package ru.lexender.project.server.storage.description;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description class.
 */
@ToString
@Getter
public class Coordinates {
    @Expose private Long x; //Поле не может быть null
    @Expose private long y; //Максимальное значение поля: 159

    public Coordinates(@NonNull Long x, long y) throws IllegalAccessException {
        if (y > 159) throw new IllegalAccessException("Y value must be less that 159");
        this.x = x;
        this.y = y;
    }

    public Coordinates(ResultSet resultSet) throws SQLException, IllegalAccessException {
        this.x = Long.parseLong(resultSet.getString("coordinates_x"));
        this.y = Long.parseLong(resultSet.getString("coordinates_y"));

        if (y > 159) throw new IllegalAccessException("Y value must be less that 159");
    }
}
