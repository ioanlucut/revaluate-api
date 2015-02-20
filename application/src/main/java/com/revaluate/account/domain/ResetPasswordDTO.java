package com.revaluate.account.domain;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@GeneratePojoBuilder
public class ResetPasswordDTO {

    @NotBlank
    @Size(min = 7)
    private String password;

    @NotBlank
    @Size(min = 7)
    private String passwordConfirmation;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}