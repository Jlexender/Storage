package ru.lexender.project.server.handler.builder.list;

import ru.lexender.project.inbetween.validator.EnumValidator;
import ru.lexender.project.inbetween.validator.LongValidator;
import ru.lexender.project.inbetween.validator.NameValidator;
import ru.lexender.project.inbetween.validator.Validator;
import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.storage.description.Color;
import ru.lexender.project.server.storage.description.Coordinates;
import ru.lexender.project.server.storage.description.Country;
import ru.lexender.project.server.storage.description.FormOfEducation;
import ru.lexender.project.server.storage.description.Person;
import ru.lexender.project.server.storage.description.Semester;
import ru.lexender.project.server.storage.description.StudyGroup;
import ru.lexender.project.server.storage.StorageObject;

import java.util.Arrays;
import java.util.List;

/**
 * Class for initializing a StorageObject object when StudyGroup is parameter.
 * To be determined.
 * @see ru.lexender.project.server.handler.builder.StorageObjectBuilder
 */
public class StudyGroupBuilder extends StorageObjectBuilder {
    public static final List<Validator> validators = Arrays.asList(
            new NameValidator(),
            new LongValidator((long) 1, null),
            new LongValidator((long) 1, null),
            new LongValidator(null, null),
            new LongValidator(null, (long) 158),
            new Validator(),
            new Validator(),
            new NameValidator(),
            new LongValidator((long) 1, null),
            new Validator(),
            new Validator(),
            new Validator()
    );

    private static final List<Validator> serverValidators = Arrays.asList(
            new NameValidator(),
            new LongValidator((long) 1, null),
            new LongValidator((long) 1, null),
            new LongValidator(null, null),
            new LongValidator(null, (long) 158),
            new EnumValidator(FormOfEducation.values()),
            new EnumValidator(Semester.values()),
            new NameValidator(),
            new LongValidator((long) 1, null),
            new EnumValidator(Color.values()),
            new EnumValidator(Color.values()),
            new EnumValidator(Country.values())
    );


    public StudyGroupBuilder() {
        super(3, Arrays.asList(
                "group name",
                "students count",
                "average mark",
                "coordinate x",
                "coordinate y",
                "form of education",
                "semester",
                "group admin name",
                "group admin weight",
                "eye color",
                "hair color",
                "nationality"
        ));

    }

    public Object[] suggest(int pointer) {
        return switch (pointer) {
            case 5: yield FormOfEducation.values();
            case 6: yield Semester.values();
            case 9,10: yield Color.values();
            case 11: yield Country.values();
            default: yield new Object[0];
        };
    }


    public boolean validateArgument(String argument, int pointer) {
        try {
            return serverValidators.get(pointer).test(argument);
        } catch (Exception exception) {
            return false;
        }
    }

    public StorageObject build(List<String> arguments)
            throws IllegalAccessException, BuildFailedException {

        try {
            String name = arguments.get(0);
            long studentsCount = Long.parseLong(arguments.get(1));
            Long averageMark = Long.parseLong(arguments.get(2));
            Long coordinateX = Long.parseLong(arguments.get(3));
            long coordinateY = Long.parseLong(arguments.get(4));
            FormOfEducation formOfEducation = FormOfEducation.valueOf(arguments.get(5));
            Semester semesterEnum = Semester.valueOf(arguments.get(6));
            String personName = arguments.get(7);
            int personWeight = Integer.parseInt(arguments.get(8));
            Color personEyeColor = Color.valueOf(arguments.get(9));
            Color persoHairColor = Color.valueOf(arguments.get(10));
            Country personNationality = Country.valueOf(arguments.get(11));

            return new StorageObject(new StudyGroup(
                    name,
                    new Coordinates(
                            coordinateX,
                            coordinateY
                    ),
                    studentsCount,
                    averageMark,
                    formOfEducation,
                    semesterEnum,
                    new Person(
                            personName,
                            personWeight,
                            personEyeColor,
                            persoHairColor,
                            personNationality
                    )
            ), false);
        } catch (Exception exception) {
            throw new BuildFailedException(exception);
        }
    }
}
