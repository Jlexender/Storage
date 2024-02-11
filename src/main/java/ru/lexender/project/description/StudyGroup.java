package ru.lexender.project.description;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import ru.lexender.project.storage.object.StorageObject;


@ToString @Getter @EqualsAndHashCode(callSuper = false)
public class StudyGroup extends StorageObject {
    @Expose private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Expose @NonNull private String name; //Поле не может быть null, Строка не может быть пустой
    @Expose @NonNull private Coordinates coordinates; //Поле не может быть null
    @Expose @NonNull private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
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

    public void update(Object[] orderedFields) throws IllegalAccessException {
        if (orderedFields.length != getOrderedFields().length)
            throw new IllegalAccessException("Invalid arguments amount");

        this.name = (String) orderedFields[0];
        this.coordinates = (Coordinates) orderedFields[1];
        this.studentsCount = (long) orderedFields[2];
        this.averageMark = (long) orderedFields[3];
        this.formOfEducation = (FormOfEducation) orderedFields[4];
        this.semesterEnum = (Semester) orderedFields[5];
        this.groupAdmin = (Person) orderedFields[6];

        if (name.isBlank()) throw new IllegalAccessException("Name can't be empty string");
        if (studentsCount < 0) throw new IllegalAccessException("StudentsCount must be greater than 0");
        if (averageMark < 0) throw new IllegalAccessException("AverageMark value must be greater than 0");
    }

    public Object[] getOrderedFields() {
        return new Object[] {
                name,
                coordinates,
                studentsCount,
                averageMark,
                formOfEducation,
                semesterEnum,
                groupAdmin
        };
    }

    public int compareTo(StudyGroup group) {
        return Long.compare(this.getId(), group.getId());
    }
}
