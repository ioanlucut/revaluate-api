package com.revaluate.domain.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.groups.*;
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
    private Integer id;

    @NotBlank(groups = {CreateViaOauthUserGroup.class, CreateUserGroup.class, UpdateUserAccountDetailsGroup.class})
    @JsonView({Views.StrictView.class})
    private String firstName;

    @NotNull(groups = {CreateViaOauthUserGroup.class})
    @JsonView({Views.StrictView.class})
    private UserType userType;

    @NotBlank(groups = {CreateViaOauthUserGroup.class, CreateUserGroup.class, UpdateUserAccountDetailsGroup.class})
    @JsonView({Views.StrictView.class})
    private String lastName;

    @Email(groups = {CreateViaOauthUserGroup.class, CreateUserGroup.class})
    @NotBlank(groups = {CreateViaOauthUserGroup.class, CreateUserGroup.class})
    @JsonView({Views.StrictView.class})
    private String email;

    @NotBlank(groups = CreateUserGroup.class)
    @Size(min = 7, groups = CreateUserGroup.class)
    private String password;

    @NotNull(groups = {CreateViaOauthUserGroup.class, CreateUserGroup.class, UpdateUserCurrencyGroup.class, UpdateUserInitiatedStatusGroup.class})
    @JsonView({Views.StrictView.class})
    private CurrencyDTO currency;

    @JsonView({Views.StrictView.class})
    @NotNull(groups = {UpdateUserInitiatedStatusGroup.class})
    private boolean initiated;

    @JsonView({Views.StrictView.class})
    private boolean emailConfirmed;

    @JsonView({Views.StrictView.class})
    private boolean connectedViaOauth;

    @JsonView({Views.StrictView.class})
    private LocalDateTime createdDate;

    @JsonView({Views.StrictView.class})
    private LocalDateTime modifiedDate;

    @JsonView({Views.StrictView.class})
    private LocalDateTime endTrialDate;

    @JsonView({Views.StrictView.class})
    private UserSubscriptionStatus userSubscriptionStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

    public boolean isConnectedViaOauth() {
        return connectedViaOauth;
    }

    public void setConnectedViaOauth(boolean connectedViaOauth) {
        this.connectedViaOauth = connectedViaOauth;
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

    public LocalDateTime getEndTrialDate() {
        return endTrialDate;
    }

    public void setEndTrialDate(LocalDateTime endTrialDate) {
        this.endTrialDate = endTrialDate;
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
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(initiated, userDTO.initiated) &&
                Objects.equals(emailConfirmed, userDTO.emailConfirmed) &&
                Objects.equals(connectedViaOauth, userDTO.connectedViaOauth) &&
                Objects.equals(id, userDTO.id) &&
                Objects.equals(firstName, userDTO.firstName) &&
                Objects.equals(userType, userDTO.userType) &&
                Objects.equals(lastName, userDTO.lastName) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(currency, userDTO.currency) &&
                Objects.equals(createdDate, userDTO.createdDate) &&
                Objects.equals(modifiedDate, userDTO.modifiedDate) &&
                Objects.equals(endTrialDate, userDTO.endTrialDate) &&
                Objects.equals(userSubscriptionStatus, userDTO.userSubscriptionStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, userType, lastName, email, password, currency, initiated, emailConfirmed, connectedViaOauth, createdDate, modifiedDate, endTrialDate, userSubscriptionStatus);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", userType=" + userType +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", currency=" + currency +
                ", initiated=" + initiated +
                ", emailConfirmed=" + emailConfirmed +
                ", connectedViaOauth=" + connectedViaOauth +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", endTrialDate=" + endTrialDate +
                ", userSubscriptionStatus=" + userSubscriptionStatus +
                '}';
    }
}