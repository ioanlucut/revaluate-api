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

    @NotNull
    @Column(nullable = false)
    private String displayName;

    @NotNull
    @Column(nullable = false)
    private String symbol;

    @NotNull
    @Column(nullable = false)
    private Integer numericCode;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(Integer numericCode) {
        this.numericCode = numericCode;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currencyCode='" + currencyCode + '\'' +
                ", displayName='" + displayName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", numericCode=" + numericCode +
                '}';
    }
}