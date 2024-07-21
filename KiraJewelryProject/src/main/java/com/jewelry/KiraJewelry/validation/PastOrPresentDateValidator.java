package com.jewelry.KiraJewelry.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class PastOrPresentDateValidator implements ConstraintValidator<PastOrPresentDate, Date> {

    @Override
    public void initialize(PastOrPresentDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; // null values are valid
        }
        return date.before(new Date()) || date.equals(new Date());
    }
}
