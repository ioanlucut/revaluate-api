package com.revaluate.account.persistence;

import com.revaluate.category.persistence.Category;
import com.revaluate.currency.persistence.Currency;
import com.revaluate.expense.persistence.Expense;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    public static final String USER = "user";
    protected static final String SEQ_NAME = "users_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "users_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    private static final long serialVersionUID = -1799428438852023627L;
    private static final String CURRENCY_ID = "currency_id";

    @Id
    @SequenceGenerator(name = User.SEQ_GENERATOR_NAME,
            sequenceName = User.SEQ_NAME,
            initialValue = User.SEQ_INITIAL_VALUE)
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

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = CURRENCY_ID, nullable = false)
    private Currency currency;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date registeredDate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = USER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<EmailToken> emailTokens;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = USER, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = USER, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

    @PrePersist
    void createdAt() {
        this.registeredDate = new Date();
    }

    public void addEmailToken(EmailToken emailToken) {
        if (this.emailTokens == null) {
            this.emailTokens = new ArrayList<>();
        }
        this.emailTokens.add(emailToken);
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

    public List<EmailToken> getEmailTokens() {
        return emailTokens;
    }

    public void setEmailTokens(List<EmailToken> emailTokens) {
        this.emailTokens = emailTokens;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}