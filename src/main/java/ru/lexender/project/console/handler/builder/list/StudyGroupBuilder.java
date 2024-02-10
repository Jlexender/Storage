package ru.lexender.project.console.handler.builder.list;

import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.description.Color;
import ru.lexender.project.description.Coordinates;
import ru.lexender.project.description.Country;
import ru.lexender.project.description.FormOfEducation;
import ru.lexender.project.description.Person;
import ru.lexender.project.description.Semester;
import ru.lexender.project.description.StudyGroup;
import ru.lexender.project.storage.object.StorageObject;

import java.util.List;
import java.util.Optional;

public class StudyGroupBuilder extends StorageObjectBuilder {

    public StudyGroupBuilder() {
        super(12, new String[]
                {
                        "name",
                        "coordinates.x",
                        "coordinates.y",
                        "studentsCount",
                        "averageMark",
                        "formOfEducation",
                        "semesterEnum",
                        "groupAdmin.name",
                        "groupAdmin.weight",
                        "groupAdmin.eyeColor",
                        "groupAdmin.hairColor",
                        "groupAdmin.nationality"
                });

    }
    public Optional<StorageObject> build(List<String> arguments) {
        try {
            if (arguments.size() != getArgumentsAmount()) return Optional.empty();
            String name = arguments.get(0);
            Long x_Coordinates = Long.parseLong(arguments.get(1));
            long y_Coordinates = Long.parseLong(arguments.get(2));
            long studentsCount = Long.parseLong(arguments.get(3));
            Long averageMark = Long.parseLong(arguments.get(4));
            FormOfEducation formOfEducation = FormOfEducation.valueOf(arguments.get(5));
            Semester semesterEnum = Semester.valueOf(arguments.get(6));
            String name_Person = arguments.get(7);
            int weight_Person = Integer.parseInt(arguments.get(8));
            Color eyeColor_Person = Color.valueOf(arguments.get(9));
            Color hairColor_Person = Color.valueOf(arguments.get(10));
            Country nationality_Person = Country.valueOf(arguments.get(11));
            Coordinates coordinates = new Coordinates(x_Coordinates, y_Coordinates);
            Person groupAdmin = new Person(name_Person, weight_Person, eyeColor_Person, hairColor_Person, nationality_Person);
            return Optional.of(new StudyGroup(name, coordinates, studentsCount,
                    averageMark, formOfEducation, semesterEnum, groupAdmin));
        } catch (IllegalAccessException exception) {
            System.out.println(exception.getMessage());
            return Optional.empty();
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
