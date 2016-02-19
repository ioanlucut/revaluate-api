package com.revaluate.validators;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class HexValidator implements ConstraintValidator<HexColor, String> {

    private static final String HEX_REGEX = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    private static final Pattern HEX_PATTERN_COMPILED = Pattern.compile(HEX_REGEX);

    @Override
    public void initialize(HexColor constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringUtils.isBlank(value) && HEX_PATTERN_COMPILED.matcher(value).matches();

    }
}
