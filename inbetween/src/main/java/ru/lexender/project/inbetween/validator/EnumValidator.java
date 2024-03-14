package ru.lexender.project.inbetween.validator;

public class EnumValidator extends Validator {
    private final Object[] values;

    public EnumValidator(Object[] values) {
        this.values = values;
    }

    public boolean test(String object) {
        for (Object o: values) {
            if (object.equals(o.toString())) return true;
        }
        return false;
    }
}
