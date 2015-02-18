package com.revaluate.account.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = UserEmailToken.SEQ_GENERATOR_NAME, sequenceName = UserEmailToken.SEQ_NAME, initialValue = UserEmailToken.SEQ_INITIAL_VALUE, allocationSize = UserEmailToken.ALLOCATION_SIZE)
@Table(name = "user_email_token")
public class UserEmailToken implements Serializable, Comparable<UserEmailToken> {

    private static final long serialVersionUID = -1799428438852023627L;

    protected static final String SEQ_NAME = "user_email_token_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "user_email_token_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    protected static final int ALLOCATION_SIZE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private String token;

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

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    @Override
    public int compareTo(UserEmailToken o) {
        return Integer.compare(this.getId(), o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEmailToken that = (UserEmailToken) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}