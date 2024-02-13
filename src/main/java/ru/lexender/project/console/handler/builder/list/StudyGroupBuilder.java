package ru.lexender.project.console.handler.builder.list;

import ru.lexender.project.console.controller.Controller;
import ru.lexender.project.console.handler.builder.ObjectBuilder;
import ru.lexender.project.console.handler.builder.StorageObjectBuilder;
import ru.lexender.project.description.Color;
import ru.lexender.project.description.Coordinates;
import ru.lexender.project.description.Country;
import ru.lexender.project.description.FormOfEducation;
import ru.lexender.project.description.Person;
import ru.lexender.project.description.Semester;
import ru.lexender.project.description.StudyGroup;
import ru.lexender.project.exception.console.handler.ObjectBuilderException;
import ru.lexender.project.storage.object.StorageObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for initializing a StorageObject object when StudyGroup is parameter.
 * @see ru.lexender.project.console.handler.builder.StorageObjectBuilder
 */
public class StudyGroupBuilder extends ObjectBuilder implements StorageObjectBuilder {

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
    public StorageObject<StudyGroup> build(List<String> arguments, Controller controller) throws ObjectBuilderException {
        try {
            if (arguments.size() != getFirstArgumentsAmount()) {
                throw new ObjectBuilderException(
                        String.format(
                                "Wrong field amount: %d arguments expected, got %d",
                                getFirstArgumentsAmount(), arguments.size())
                );
            }

            List<Object> constructorArgs = new ArrayList<>();

            String name = arguments.get(0);
            long studentsCount = Long.parseLong(arguments.get(1));
            Long averageMark = Long.parseLong(arguments.get(2));

            if (name.isBlank()) throw new IllegalAccessException("Name can't be empty string");
            if (studentsCount < 0) throw new IllegalAccessException("Students count must be greater than 0");
            if (averageMark < 0) throw new IllegalAccessException("AverageMark value must be greater than 0");

            constructorArgs.add(name);
            constructorArgs.add(studentsCount);
            constructorArgs.add(averageMark);

            for (int i = getFirstArgumentsAmount(); i < getFieldNames().size(); ++i) {
                Object newArgument = capture(controller, i);
                constructorArgs.add(newArgument);
            }

            return new StorageObject<>(new StudyGroup(
                    (String)constructorArgs.get(0),
                    new Coordinates(
                            (long)constructorArgs.get(3),
                            (long)constructorArgs.get(4)
                    ),
                    (long)constructorArgs.get(1),
                    (Long)constructorArgs.get(2),
                    (FormOfEducation)constructorArgs.get(5),
                    (Semester)constructorArgs.get(6),
                    new Person(
                            (String)constructorArgs.get(7),
                            (int)constructorArgs.get(8),
                            (Color)constructorArgs.get(9),
                            (Color)constructorArgs.get(10),
                            (Country)constructorArgs.get(11)
                        )
            ));
        } catch (Exception exception) {
            throw new ObjectBuilderException(exception);
        }
    }

    public Object capture(Controller controller, int pointer) {
        for (;;) {
            controller.getSender().send("Enter " + getFieldNames().get(pointer) + ':');

            switch (pointer) {
                case 5:
                    controller.getSender().send(Arrays.asList(FormOfEducation.values()));
                    break;
                case 6:
                    controller.getSender().send(Arrays.asList(Semester.values()));
                    break;
                case 9,10:
                    controller.getSender().send(Arrays.asList(Color.values()));
                    break;
                case 11:
                    controller.getSender().send(Arrays.asList(Country.values()));
                    break;
            }

            String message = controller.getReceiver().receive();

            try {
                return switch (pointer) {
                    case 3: yield Long.parseLong(message);
                    case 4:
                        long yCoordinate = Long.parseLong(message);
                        if (yCoordinate < 159) yield yCoordinate;
                        throw new IllegalAccessException("Y value must be less than 159");
                    case 5: yield FormOfEducation.valueOf(message);
                    case 6: yield Semester.valueOf(message);
                    case 7:
                        if (message.isBlank()) throw new IllegalAccessException("Name can't be empty string");
                        yield message;
                    case 8:
                        int personWeight = Integer.parseInt(message);
                        if (personWeight > 0) yield personWeight;
                        throw new IllegalAccessException("Weight must be positive");
                    case 9, 10: yield Color.valueOf(message);
                    case 11: yield Country.valueOf(message);
                    default: throw new Exception();
                };
            } catch (IllegalAccessException exception) {
                controller.getSender().send(exception.getMessage());
            } catch (Exception exception) {
                controller.getSender().send("Invalid argument. Please enter valid argument.");
            }
        }
    }

    public StorageObject<StudyGroup> buildInLine(List<String> arguments, Controller controller) throws ObjectBuilderException {
        try {
            if (arguments.size() != getFieldNames().size()) {
                throw new ObjectBuilderException(
                        String.format(
                                "Wrong field amount: %d arguments expected, got %d",
                                getFieldNames().size(), arguments.size())
                );
            }

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


            if (name.isBlank()) throw new IllegalAccessException("Name can't be empty string");
            if (studentsCount < 0) throw new IllegalAccessException("Students count must be greater than 0");
            if (averageMark < 0) throw new IllegalAccessException("AverageMark value must be greater than 0");
            if (coordinateY >= 159) throw new IllegalAccessException("Y value must be less than 159");
            if (personName.isBlank()) throw new IllegalAccessException("Name can't be empty string");
            if (personWeight <= 0) throw new IllegalAccessException("Weight must be positive");


            return new StorageObject<>(new StudyGroup(
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
            ));
        } catch (Exception exception) {
            throw new ObjectBuilderException(exception);
        }
    }
}
