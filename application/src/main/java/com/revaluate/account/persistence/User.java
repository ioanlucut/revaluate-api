package com.revaluate.account.persistence;

import com.revaluate.currency.persistence.Currency;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.domain.account.UserType;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "users",
        indexes = {
                @Index(name = User.IX_USER_EMAIL_USER_TYPE, columnList = "email,userType")
        }
)
public class User implements Serializable {

    private static final String CURRENCY_ID = "currency_id";
    static final String IX_USER_EMAIL_USER_TYPE = "IX_USER_EMAIL_USER_TYPE";
    private static final String SEQ_NAME = "users_id_seq";
    private static final String SEQ_GENERATOR_NAME = "users_seq_generator";
    private static final int SEQ_INITIAL_VALUE = 1;
    private static final long serialVersionUID = -1799428438852023627L;
    @Id
    @SequenceGenerator(name = User.SEQ_GENERATOR_NAME,
            sequenceName = User.SEQ_NAME,
            initialValue = User.SEQ_INITIAL_VALUE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Column(nullable = false)
    private String lastName;

    /**
     * User is logged based on the email and password (not username and password)
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    /**
     * The user can be enabled or disabled from administration platform.
     */
    private boolean enabled;

    /**
     * If user is initiated, which means he finished the whole registration process.
     */
    private boolean initiated;

    /**
     * Is user email confirmed ?.
     */
    private boolean emailConfirmed;

    /**
     * If user is connected via oauth.
     */
    private boolean connectedViaOauth;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = CURRENCY_ID, nullable = false)
    private Currency currency;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime endTrialDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserSubscriptionStatus userSubscriptionStatus;

    @PrePersist
    void createdAt() {
        this.createdDate = this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.modifiedDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInitiated() {
        return initiated;
    }

    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public boolean isUserTrialPeriodExpired() {
        LocalDateTime endTrialDate = getEndTrialDate();

        return LocalDateTime.now().isAfter(endTrialDate);
    }

    public boolean isConnectedViaOauth() {
        return connectedViaOauth;
    }

    public void setConnectedViaOauth(boolean connectedViaOauth) {
        this.connectedViaOauth = connectedViaOauth;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", initiated=" + initiated +
                ", emailConfirmed=" + emailConfirmed +
                ", connectedViaOauth=" + connectedViaOauth +
                ", currency=" + currency +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", endTrialDate=" + endTrialDate +
                ", userSubscriptionStatus=" + userSubscriptionStatus +
                ", userType=" + userType +
                '}';
    }
}