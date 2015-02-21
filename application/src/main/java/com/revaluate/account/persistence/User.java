package com.revaluate.account.persistence;

import com.revaluate.category.persistence.Category;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = User.SEQ_GENERATOR_NAME, sequenceName = User.SEQ_NAME, initialValue = User.SEQ_INITIAL_VALUE, allocationSize = User.ALLOCATION_SIZE)
@Table(name = "users")
public class User implements Serializable {

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

    private boolean initiated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date registeredDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reset_email_token_id")
    private UserEmailToken resetEmailToken;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

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

    public boolean isInitiated() {
        return initiated;
    }

    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public Date getRegisteredDate() {
        return registeredDate != null ? new Date(registeredDate.getTime()) : null;
    }

    public void setRegisteredDate(Date registeredDate) {
        if (registeredDate != null) {
            this.registeredDate = new Date(registeredDate.getTime());
        }
    }

    public UserEmailToken getResetEmailToken() {
        return resetEmailToken;
    }

    public void setResetEmailToken(UserEmailToken resetEmailToken) {
        this.resetEmailToken = resetEmailToken;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}