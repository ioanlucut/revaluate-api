package com.revaluate.email.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.domain.email.EmailType;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = Email.SEQ_GENERATOR_NAME, sequenceName = Email.SEQ_NAME, initialValue = Email.SEQ_INITIAL_VALUE, allocationSize = Email.ALLOCATION_SIZE)
@Table(name = "email")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = Email.EMAIL_TYPE)
public abstract class Email implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    protected static final String SEQ_NAME = "user_token_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "user_token_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    protected static final int ALLOCATION_SIZE = 1;
    public static final String USER_ID = "user_id";
    public static final String EMAIL_TYPE = "discriminator_email_type";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailType emailType;

    @ManyToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;

    /**
     * Is email sent flag
     */
    private boolean sent;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime sentDate;

    @PrePersist
    void createdAt() {
        this.createdDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", emailType=" + emailType +
                ", user=" + user +
                ", sent=" + sent +
                ", createdDate=" + createdDate +
                ", sentDate=" + sentDate +
                '}';
    }
}