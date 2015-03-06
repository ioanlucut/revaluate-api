package com.revaluate.currency.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "currencies")
public class Currency implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    protected static final String SEQ_NAME = "currencies_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "currencies_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;

    @Id
    @SequenceGenerator(name = Currency.SEQ_GENERATOR_NAME,
            sequenceName = Currency.SEQ_NAME,
            initialValue = Currency.SEQ_INITIAL_VALUE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String currencyCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}