package com.revaluate.core.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HexValidator.class)
public @interface HexColor {

    String message() default "The color is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}