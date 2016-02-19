package com.revaluate.validators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HexValidatorTest {

    private HexValidator hexValidator = new HexValidator();

    @Test
    public void validate_nullOrBlank_isNotOk() {
        assertThat(hexValidator.isValid(null, null)).isFalse();
        assertThat(hexValidator.isValid("", null)).isFalse();
    }

    @Test
    public void validate_validHexColors_isOk() {
        assertThat(hexValidator.isValid("#1f1f1F", null)).isTrue();
        assertThat(hexValidator.isValid("#AFAFAF", null)).isTrue();
        assertThat(hexValidator.isValid("#1AFFa1", null)).isTrue();
        assertThat(hexValidator.isValid("#222fff", null)).isTrue();
        assertThat(hexValidator.isValid("#F00", null)).isTrue();
    }

    @Test
    public void validate_invalidHexColors_isOk() {
        assertThat(hexValidator.isValid("123456", null)).isFalse();
        assertThat(hexValidator.isValid("12345", null)).isFalse();
        assertThat(hexValidator.isValid("12", null)).isFalse();
        assertThat(hexValidator.isValid("1", null)).isFalse();
        assertThat(hexValidator.isValid("1", null)).isFalse();
    }
}