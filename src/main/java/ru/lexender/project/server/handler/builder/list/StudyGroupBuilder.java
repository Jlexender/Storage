package ru.lexender.project.server.handler.builder.list;

import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.handler.builder.ObjectBuilder;
import ru.lexender.project.server.handler.builder.StorageObjectBuilder;
import ru.lexender.project.server.storage.description.Color;
import ru.lexender.project.server.storage.description.Coordinates;
import ru.lexender.project.server.storage.description.Country;
import ru.lexender.project.server.storage.description.FormOfEducation;
import ru.lexender.project.server.storage.description.Person;
import ru.lexender.project.server.storage.description.Semester;
import ru.lexender.project.server.storage.description.StudyGroup;
import ru.lexender.project.server.invoker.Invoker;
import ru.lexender.project.server.storage.object.StorageObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for initializing a StorageObject object when StudyGroup is parameter.
 * To be determined.
 * @see ru.lexender.project.server.handler.builder.StorageObjectBuilder
 */
public class StudyGroupBuilder extends StorageObjectBuilder {

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
//    public StorageObject<StudyGroup> build(List<String> arguments, Invoker invoker) throws BuildFailedException {
//        if (!isValid(arguments)) {
//            return buildInLine(arguments, invoker);
//        }
//        try {
//
//
//            String name = arguments.get(0);
//            long studentsCount = Long.parseLong(arguments.get(1));
//            Long averageMark = Long.parseLong(arguments.get(2));
//
//            if (name.isBlank()) throw new IllegalAccessException("Name can't be empty string");
//            if (studentsCount < 0) throw new IllegalAccessException("Students count must be greater than 0");
//            if (averageMark < 0) throw new IllegalAccessException("AverageMark value must be greater than 0");
//
//
//            List<Object> constructorArgs = new ArrayList<>();
//            constructorArgs.add(name);
//            constructorArgs.add(studentsCount);
//            constructorArgs.add(averageMark);
//
//            for (int i = getFirstArgumentsAmount(); i < getFieldNames().size(); ++i) {
//                Object newArgument = capture(invoker, i);
//                constructorArgs.add(newArgument);
//            }
//
//            return new StorageObject<>(new StudyGroup(
//                    (String)constructorArgs.get(0),
//                    new Coordinates(
//                            (long)constructorArgs.get(3),
//                            (long)constructorArgs.get(4)
//                    ),
//                    (long)constructorArgs.get(1),
//                    (Long)constructorArgs.get(2),
//                    (FormOfEducation)constructorArgs.get(5),
//                    (Semester)constructorArgs.get(6),
//                    new Person(
//                            (String)constructorArgs.get(7),
//                            (int)constructorArgs.get(8),
//                            (Color)constructorArgs.get(9),
//                            (Color)constructorArgs.get(10),
//                            (Country)constructorArgs.get(11)
//                        )
//            ), false);
//        } catch (Exception exception) {
//            throw new BuildFailedException(exception);
//        }
//    }
//
//    public Object capture(Invoker invoker, int pointer) {
//        for (;;) {
//            invoker.getSender().send("Enter " + getFieldNames().get(pointer) + ':');
//
//            switch (pointer) {
//                case 5:
//                    invoker.getSender().send(Arrays.asList(FormOfEducation.values()));
//                    break;
//                case 6:
//                    invoker.getSender().send(Arrays.asList(Semester.values()));
//                    break;
//                case 9,10:
//                    invoker.getSender().send(Arrays.asList(Color.values()));
//                    break;
//                case 11:
//                    invoker.getSender().send(Arrays.asList(Country.values()));
//                    break;
//            }
//
//            String message = invoker.getReceiver().receive().toString();
//
//            try {
//                return switch (pointer) {
//                    case 3: yield Long.parseLong(message);
//                    case 4:
//                        long yCoordinate = Long.parseLong(message);
//                        if (yCoordinate < 159) yield yCoordinate;
//                        throw new IllegalAccessException("Y value must be less than 159");
//                    case 5: yield FormOfEducation.valueOf(message);
//                    case 6: yield Semester.valueOf(message);
//                    case 7:
//                        if (message.isBlank()) throw new IllegalAccessException("Name can't be empty string");
//                        yield message;
//                    case 8:
//                        int personWeight = Integer.parseInt(message);
//                        if (personWeight > 0) yield personWeight;
//                        throw new IllegalAccessException("Weight must be positive");
//                    case 9, 10: yield Color.valueOf(message);
//                    case 11: yield Country.valueOf(message);
//                    default: throw new Exception();
//                };
//            } catch (IllegalAccessException exception) {
//                invoker.getSender().send(exception.getMessage());
//            } catch (Exception exception) {
//                invoker.getSender().send("Invalid argument. Please enter valid argument.");
//            }
//        }
//    }

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
                var value =  switch (pointer) {
                    case 0: yield argument;
                    case 1,2,3: yield Long.parseLong(argument);
                    case 4:
                        long yCoordinate = Long.parseLong(argument);
                        if (yCoordinate < 159) yield yCoordinate;
                        throw new IllegalAccessException("Y value must be less than 159");
                    case 5: yield FormOfEducation.valueOf(argument);
                    case 6: yield Semester.valueOf(argument);
                    case 7:
                        if (argument.isBlank()) throw new IllegalAccessException("Name can't be empty string");
                        yield argument;
                    case 8:
                        int personWeight = Integer.parseInt(argument);
                        if (personWeight > 0) yield personWeight;
                        throw new IllegalAccessException("Weight must be positive");
                    case 9, 10: yield Color.valueOf(argument);
                    case 11: yield Country.valueOf(argument);
                    default: throw new Exception();
                };
                return true;
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
