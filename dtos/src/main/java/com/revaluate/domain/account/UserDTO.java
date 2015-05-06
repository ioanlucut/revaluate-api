package com.revaluate.domain.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.groups.CreateUserGroup;
import com.revaluate.groups.UpdateUserCurrencyGroup;
import com.revaluate.views.Views;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@GeneratePojoBuilder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @JsonView({Views.StrictView.class})
    private int id;

    @NotBlank(groups = CreateUserGroup.class)
    @JsonView({Views.StrictView.class})
    private String firstName;

    @NotBlank(groups = CreateUserGroup.class)
    @JsonView({Views.StrictView.class})
    private String lastName;

    @Email(groups = CreateUserGroup.class)
    @NotBlank(groups = CreateUserGroup.class)
    @JsonView({Views.StrictView.class})
    private String email;

    @NotBlank(groups = CreateUserGroup.class)
    @Size(min = 7, groups = CreateUserGroup.class)
    private String password;

    @NotNull(groups = {CreateUserGroup.class, UpdateUserCurrencyGroup.class})
    @JsonView({Views.StrictView.class})
    private CurrencyDTO currency;

    @JsonView({Views.StrictView.class})
    private boolean initiated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

    public boolean isInitiated() {
        return initiated;
    }

    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (id != userDTO.id) return false;
        if (initiated != userDTO.initiated) return false;
        if (email != null ? !email.equals(userDTO.email) : userDTO.email != null) return false;
        if (firstName != null ? !firstName.equals(userDTO.firstName) : userDTO.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userDTO.lastName) : userDTO.lastName != null) return false;
        if (password != null ? !password.equals(userDTO.password) : userDTO.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (initiated ? 1 : 0);
        return result;
    }
}