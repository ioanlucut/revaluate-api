package com.revaluate.domain.account;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@GeneratePojoBuilder
public class LoginDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 7)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}