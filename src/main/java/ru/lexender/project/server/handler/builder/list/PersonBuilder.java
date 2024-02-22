package ru.lexender.project.server.handler.builder.list;

import ru.lexender.project.server.exception.io.handling.BuildFailedException;
import ru.lexender.project.server.handler.builder.ObjectBuilder;
import ru.lexender.project.server.storage.description.Color;
import ru.lexender.project.server.storage.description.Country;
import ru.lexender.project.server.storage.description.Person;

import java.util.Arrays;
import java.util.List;

/**
 * Class for initializing a Person object.
 * To be determined
 *
 * @see ru.lexender.project.server.handler.builder.ObjectBuilder
 */
public class PersonBuilder extends ObjectBuilder {

    public PersonBuilder() {
        super(2, Arrays.asList(
                "name",
                "weight",
                "eye color",
                "hair color",
                "nationality"
        ));

    }

    public boolean validateArgument(String argument, int pointer) {
        try {
            var value = switch (pointer) {
                case 0:
                    if (argument.isBlank()) throw new IllegalAccessException("Name can't be empty string");
                    yield argument;
                case 1:
                    if (Long.parseLong(argument) <= 0) throw new IllegalAccessException("Weight must be positive");
                    yield Long.parseLong(argument);
                case 2, 3: yield Color.valueOf(argument);
                case 4: yield Country.valueOf(argument);
                default: throw new Exception();
            };
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public Object[] suggest(int pointer) {
        return switch (pointer) {
            case 2, 3: yield Color.values();
            case 4: yield Country.values();
            default: yield new Object[0];
        };
    }

    public Person build(List<String> arguments) throws BuildFailedException, IllegalAccessException {
        String personName = arguments.get(0);
        int personWeight = Integer.parseInt(arguments.get(1));
        Color personEyeColor = Color.valueOf(arguments.get(2));
        Color persoHairColor = Color.valueOf(arguments.get(3));
        Country personNationality = Country.valueOf(arguments.get(4));

        return new Person(
            personName,
            personWeight,
            personEyeColor,
            persoHairColor,
            personNationality
        );

    }
}
