package ru.lexender.project.server.handler.builder.list;

import ru.lexender.project.inbetween.validator.EnumValidator;
import ru.lexender.project.inbetween.validator.LongValidator;
import ru.lexender.project.inbetween.validator.NameValidator;
import ru.lexender.project.inbetween.validator.Validator;
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
    public static final List<Validator> validators = Arrays.asList(
            new NameValidator(),
            new LongValidator((long) 1, null),
            new Validator(),
            new Validator(),
            new Validator()
    );

    public static final List<Validator> serverValidators = Arrays.asList(
            new NameValidator(),
            new LongValidator((long) 1, null),
            new EnumValidator(Color.values()),
            new EnumValidator(Color.values()),
            new EnumValidator(Country.values())
    );

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
            return serverValidators.get(pointer).test(argument);
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
