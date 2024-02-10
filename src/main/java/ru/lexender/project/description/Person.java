package ru.lexender.project.description;


import com.google.gson.annotations.Expose;
import lombok.NonNull;
import lombok.ToString;

@ToString
public class Person {
    @Expose @NonNull private String name; //Поле не может быть null, Строка не может быть пустой
    @Expose @NonNull private int weight; //Значение поля должно быть больше 0
    @Expose @NonNull private Color eyeColor; //Поле не может быть null
    @Expose @NonNull private Color hairColor; //Поле не может быть null
    @Expose @NonNull private Country nationality; //Поле не может быть null

    public Person(String name,
                  int weight,
                  Color eyeColor,
                  Color hairColor,
                  Country nationality) throws IllegalAccessException {
        if (name.isBlank()) throw new IllegalAccessException("Name can't be empty string");
        this.name = name;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }
}
