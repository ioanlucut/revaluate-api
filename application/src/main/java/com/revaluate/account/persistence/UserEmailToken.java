package com.revaluate.account.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = UserEmailToken.SEQ_GENERATOR_NAME, sequenceName = UserEmailToken.SEQ_NAME, initialValue = UserEmailToken.SEQ_INITIAL_VALUE, allocationSize = UserEmailToken.ALLOCATION_SIZE)
@Table(name = "user_email_token")
public class UserEmailToken implements Serializable {

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
}