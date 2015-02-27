package com.revaluate.account.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.account.entityfiltering.Views;
import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.expense.domain.ExpenseDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@GeneratePojoBuilder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    private int id;

    @NotBlank
    @JsonView({Views.UserView.class})
    private String firstName;

    @NotBlank
    @JsonView({Views.UserView.class})
    private String lastName;

    @Email
    @NotBlank
    @JsonView({Views.UserView.class})
    private String email;

    @NotBlank
    @Size(min = 7)
    @JsonView({Views.UserView.class})
    private String password;

    @JsonView({Views.UserView.class})
    private boolean initiated;

    @JsonView({Views.UserDetails.class})
    private List<CategoryDTO> categories = new ArrayList<>();

    @JsonView({Views.UserDetails.class})
    private List<ExpenseDTO> expenses = new ArrayList<>();

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

    public boolean isInitiated() {
        return initiated;
    }

    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<ExpenseDTO> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDTO> expenses) {
        this.expenses = expenses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (id != userDTO.id) return false;
        if (initiated != userDTO.initiated) return false;
        if (categories != null ? !categories.equals(userDTO.categories) : userDTO.categories != null) return false;
        if (email != null ? !email.equals(userDTO.email) : userDTO.email != null) return false;
        if (expenses != null ? !expenses.equals(userDTO.expenses) : userDTO.expenses != null) return false;
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
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (expenses != null ? expenses.hashCode() : 0);
        return result;
    }
}