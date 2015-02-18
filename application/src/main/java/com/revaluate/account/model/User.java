package com.revaluate.account.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@SequenceGenerator(name = User.SEQ_GENERATOR_NAME, sequenceName = User.SEQ_NAME, initialValue = User.SEQ_INITIAL_VALUE, allocationSize = User.ALLOCATION_SIZE)
@Table(name = "users")
public class User implements Serializable, Comparable<User> {

    private static final long serialVersionUID = -1799428438852023627L;

    protected static final String SEQ_NAME = "users_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "users_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 600000;
    protected static final int ALLOCATION_SIZE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

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

    @NotNull
    @Column(nullable = false)
    private String password;

    /**
     * The user can be enabled or disabled from administration platform.
     */
    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date registeredDate;

    @OneToOne
    @JoinColumn(name = "user_email_token_id")
    private UserEmailToken emailToken;

    @PrePersist
    void createdAt() {
        this.registeredDate = new Date();
    }

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

    public Date getRegisteredDate() {
        return registeredDate != null ? new Date(registeredDate.getTime()) : null;
    }

    public void setRegisteredDate(Date registeredDate) {
        if (registeredDate != null) {
            this.registeredDate = new Date(registeredDate.getTime());
        }
    }

    public UserEmailToken getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(UserEmailToken emailToken) {
        this.emailToken = emailToken;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int compareTo(User o) {
        return getEmail().compareTo(o.getEmail());
    }

}