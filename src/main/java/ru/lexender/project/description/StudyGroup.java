package ru.lexender.project.description;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import ru.lexender.project.storage.object.StorageObject;

import java.time.LocalDateTime;



@ToString @Getter @EqualsAndHashCode(callSuper = false)
public class StudyGroup extends StorageObject {
    @Expose private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Expose private String name; //Поле не может быть null, Строка не может быть пустой
    @Expose private Coordinates coordinates; //Поле не может быть null
    @Expose private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Expose private long studentsCount; //Значение поля должно быть больше 0
    @Expose private Long averageMark; //Значение поля должно быть больше 0, Поле может быть null
    @Expose private FormOfEducation formOfEducation; //Поле может быть null
    @Expose private Semester semesterEnum; //Поле может быть null
    @Expose private Person groupAdmin; //Поле не может быть null

    public StudyGroup(@NonNull String name,
                  @NonNull Coordinates coordinates,
                  long studentsCount,
                  @NonNull Long averageMark,
                  @NonNull FormOfEducation formOfEducation,
                  @NonNull Semester semesterEnum,
                  @NonNull Person groupAdmin) throws IllegalAccessException {
        super();

        if (name.isBlank()) throw new IllegalAccessException("Name can't be empty string");
        if (studentsCount < 0) throw new IllegalAccessException("StudentsCount must be greater than 0");
        if (averageMark < 0) throw new IllegalAccessException("AverageMark value must be greater than 0");

        this.id = super.getId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = super.getCreationDate();
        this.studentsCount = studentsCount;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public int compareTo(StudyGroup group) {
        return Long.compare(this.getId(), group.getId());
    }
}
