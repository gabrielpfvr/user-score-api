package com.gabrielmotta.userscore.api.annotation.constraints;

import com.gabrielmotta.userscore.api.annotation.Cep;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CepValidator implements ConstraintValidator<Cep, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("\\d{8}");
    }
}
