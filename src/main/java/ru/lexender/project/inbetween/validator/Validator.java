package ru.lexender.project.inbetween.validator;

import java.io.Serializable;

public class Validator implements Serializable {
    private final SerializablePredicate<String> predicate;

    public Validator(SerializablePredicate<String> predicate) {
        this.predicate = predicate;
    }

    public Validator() {
        this.predicate = o -> true;
    }

    public boolean test(String object) {
        try {
            return predicate.test(object);
        } catch (Exception exception) {
            return false;
        }
    }
}
