package com.revaluate.domain.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.groups.CreateUserGroup;
import com.revaluate.groups.UpdateUserCurrencyGroup;
import com.revaluate.views.Views;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

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

    @JsonView({Views.StrictView.class})
    private boolean emailConfirmed;

    @JsonView({Views.StrictView.class})
    private LocalDateTime createdDate;

    @JsonView({Views.StrictView.class})
    private LocalDateTime modifiedDate;

    @JsonView({Views.StrictView.class})
    private UserSubscriptionStatus userSubscriptionStatus;

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

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public UserSubscriptionStatus getUserSubscriptionStatus() {
        return userSubscriptionStatus;
    }

    public void setUserSubscriptionStatus(UserSubscriptionStatus userSubscriptionStatus) {
        this.userSubscriptionStatus = userSubscriptionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(initiated, userDTO.initiated) &&
                Objects.equals(emailConfirmed, userDTO.emailConfirmed) &&
                Objects.equals(firstName, userDTO.firstName) &&
                Objects.equals(lastName, userDTO.lastName) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(currency, userDTO.currency) &&
                Objects.equals(createdDate, userDTO.createdDate) &&
                Objects.equals(modifiedDate, userDTO.modifiedDate) &&
                Objects.equals(userSubscriptionStatus, userDTO.userSubscriptionStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, currency, initiated, emailConfirmed, createdDate, modifiedDate, userSubscriptionStatus);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", currency=" + currency +
                ", initiated=" + initiated +
                ", emailConfirmed=" + emailConfirmed +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", userSubscriptionStatus=" + userSubscriptionStatus +
                '}';
    }
}