package ru.lexender.project.inbetween.validator;

import java.io.Serializable;


public class Validator implements Serializable {
    public boolean test(String t) {
        return t != null;
    }
}
