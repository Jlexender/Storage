package ru.lexender.project.description;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import ru.lexender.project.storage.object.StorageInitializable;

/**
 * Description class, collection target.
 * @see ru.lexender.project.storage.object.StorageInitializable
 */
@ToString @Getter @EqualsAndHashCode(callSuper = false)
public class StudyGroup implements Comparable<StudyGroup>, StorageInitializable {
    @ToString.Exclude private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Expose private String name; //Поле не может быть null, Строка не может быть пустой
    @Expose private Coordinates coordinates; //Поле не может быть null
    @ToString.Exclude @NonNull private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Expose private long studentsCount; //Значение поля должно быть больше 0
    @Expose @NonNull private Long averageMark; //Значение поля должно быть больше 0, Поле может быть null
    @Expose @NonNull private FormOfEducation formOfEducation; //Поле может быть null
    @Expose @NonNull private Semester semesterEnum; //Поле может быть null
    @Expose @NonNull private Person groupAdmin; //Поле не может быть null

    public StudyGroup(String name,
                  Coordinates coordinates,
                  long studentsCount,
                  Long averageMark,
                  FormOfEducation formOfEducation,
                  Semester semesterEnum,
                  Person groupAdmin) throws IllegalAccessException {
        if (name.isBlank()) throw new IllegalAccessException("Name can't be empty string");
        if (studentsCount < 0) throw new IllegalAccessException("StudentsCount must be greater than 0");
        if (averageMark < 0) throw new IllegalAccessException("AverageMark value must be greater than 0");

        this.name = name;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public int compareTo(StudyGroup group) {
        if (this != group)
            return (this.averageMark > group.getAverageMark() ? -1 : 1);
        return 0;
    }

}
