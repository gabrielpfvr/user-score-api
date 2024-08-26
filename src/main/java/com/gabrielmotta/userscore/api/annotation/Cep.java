package com.gabrielmotta.userscore.api.annotation;

import com.gabrielmotta.userscore.api.annotation.constraints.CepValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CepValidator.class)
public @interface Cep {

    String message() default "is invalid. It must contain exactly 8 digits.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
