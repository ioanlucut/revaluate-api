package com.revaluate.account.persistence;

import com.revaluate.domain.email.EmailType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = EmailToken.SEQ_GENERATOR_NAME, sequenceName = EmailToken.SEQ_NAME, initialValue = EmailToken.SEQ_INITIAL_VALUE, allocationSize = EmailToken.ALLOCATION_SIZE)
@Table(name = "email_token")
public class EmailToken implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    protected static final String SEQ_NAME = "user_token_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "user_token_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    protected static final int ALLOCATION_SIZE = 1;
    public static final String USER_ID = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailType emailType;

    @ManyToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;

    /**
     * The user can be enabled or disabled from administration platform.
     */
    private boolean validated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    @Override
    public String toString() {
        return "EmailToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", emailType=" + emailType +
                ", user=" + user +
                ", validated=" + validated +
                '}';
    }
}