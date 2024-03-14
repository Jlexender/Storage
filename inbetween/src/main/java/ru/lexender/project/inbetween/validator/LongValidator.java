package ru.lexender.project.inbetween.validator;

public class IntervalValidator extends Validator{
    private final Long min, max;

    public IntervalValidator(Long min, Long max) {
        this.min = min;
        this.max = max;
    }

    public boolean test(String text) {
        long object;
        try {
            object = Long.parseLong(text);
        } catch (Exception exception) {
            return false;
        }

        boolean minflag = true, maxflag = true;
        if (min != null && object < min) minflag = false;
        if (max != null && object > max) maxflag = false;
        return minflag && maxflag;
    }
}
