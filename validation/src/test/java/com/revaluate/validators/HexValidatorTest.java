package com.revaluate.validators;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HexValidatorTest {

    private HexValidator hexValidator = new HexValidator();

    @Test
    public void validate_nullOrBlank_isNotOk() {
        assertThat(hexValidator.isValid(null, null), is(false));
        assertThat(hexValidator.isValid("", null), is(false));
    }

    @Test
    public void validate_validHexColors_isOk() {
        assertThat(hexValidator.isValid("#1f1f1F", null), is(true));
        assertThat(hexValidator.isValid("#AFAFAF", null), is(true));
        assertThat(hexValidator.isValid("#1AFFa1", null), is(true));
        assertThat(hexValidator.isValid("#222fff", null), is(true));
        assertThat(hexValidator.isValid("#F00", null), is(true));
    }

    @Test
    public void validate_invalidHexColors_isOk() {
        assertThat(hexValidator.isValid("123456", null), is(false));
        assertThat(hexValidator.isValid("12345", null), is(false));
        assertThat(hexValidator.isValid("12", null), is(false));
        assertThat(hexValidator.isValid("1", null), is(false));
        assertThat(hexValidator.isValid("1", null), is(false));
    }
}