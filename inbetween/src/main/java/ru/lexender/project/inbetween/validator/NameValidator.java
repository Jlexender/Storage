package ru.lexender.project.inbetween.validator;

public class NameValidator extends Validator {
    @Override
    public boolean test(String t) {
        return t != null && !t.isBlank();
    }
}
