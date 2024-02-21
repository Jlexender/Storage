package ru.lexender.project.server.io.decoder.builder.list;

import ru.lexender.project.server.exception.handler.ObjectBuilderException;
import ru.lexender.project.server.io.decoder.builder.ObjectBuilder;
import ru.lexender.project.server.description.Color;
import ru.lexender.project.server.description.Country;
import ru.lexender.project.server.description.Person;
import ru.lexender.project.server.invoker.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for initializing a Person object.
 * @see ru.lexender.project.server.io.decoder.builder.ObjectBuilder
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
    public Person build(List<String> arguments, Invoker invoker) throws ObjectBuilderException {
        if (!checkInteractive(arguments)) {
            return buildInLine(arguments, invoker);
        }

        try {
            List<Object> constructorArgs = new ArrayList<>();

            String name = arguments.get(0);
            int weight = Integer.parseInt(arguments.get(1));

            if (name.isBlank()) throw new IllegalAccessException("Name can't be empty string");
            if (weight <= 0) throw new IllegalAccessException("Weight must be positive");

            constructorArgs.add(name);
            constructorArgs.add(weight);

            for (int i = getFirstArgumentsAmount(); i < getFieldNames().size(); ++i) {
                Object newArgument = capture(invoker, i);
                constructorArgs.add(newArgument);
            }

            return new Person(
                    (String)constructorArgs.get(0),
                    (int)constructorArgs.get(1),
                    (Color)constructorArgs.get(2),
                    (Color)constructorArgs.get(3),
                    (Country)constructorArgs.get(4)
            );
        } catch (Exception exception) {
            throw new ObjectBuilderException(exception);
        }

    }

    public Object capture(Invoker invoker, int pointer) {
        for (;;) {
            invoker.getSender().send("Enter " + getFieldNames().get(pointer) + ':');

            switch (pointer) {
                case 2,3:
                    invoker.getSender().send(Arrays.asList(Color.values()));
                    break;
                case 4:
                    invoker.getSender().send(Arrays.asList(Country.values()));
                    break;
            }

            String message = invoker.getReceiver().receive().toString();

            try {
                return switch (pointer) {
                    case 2, 3: yield Color.valueOf(message);
                    case 4: yield Country.valueOf(message);
                    default: throw new Exception();
                };
            } catch (IllegalAccessException exception) {
                invoker.getSender().send(exception.getMessage());
            } catch (Exception exception) {
                invoker.getSender().send("Invalid argument. Please enter valid argument.");
            }
        }
    }

    public Person buildInLine(List<String> arguments, Invoker invoker) throws ObjectBuilderException {
        if (!checkInLine(arguments))
            throw new ObjectBuilderException(String.format(
                    "Wrong field amount: %d arguments expected (%d for interactive mode), got %d",
                    getFieldNames().size(), getFieldNames().size(), arguments.size())
            );
        try {
            String personName = arguments.get(0);
            int personWeight = Integer.parseInt(arguments.get(1));
            Color personEyeColor = Color.valueOf(arguments.get(2));
            Color persoHairColor = Color.valueOf(arguments.get(3));
            Country personNationality = Country.valueOf(arguments.get(4));


            if (personName.isBlank()) throw new IllegalAccessException("Name can't be empty string");
            if (personWeight <= 0) throw new IllegalAccessException("Weight must be positive");


            return new Person(
                personName,
                personWeight,
                personEyeColor,
                persoHairColor,
                personNationality
            );
        } catch (Exception exception) {
            throw new ObjectBuilderException(exception);
        }
    }
}
