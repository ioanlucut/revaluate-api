package com.revaluate.account.domain;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@GeneratePojoBuilder
public class UpdatePasswordDTO {

    @NotBlank
    @Size(min = 7)
    private String oldPassword;

    @NotBlank
    @Size(min = 7)
    private String newPassword;

    @NotBlank
    @Size(min = 7)
    private String newPasswordConfirmation;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}