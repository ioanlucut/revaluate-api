package com.revaluate.currency.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "currencies",
        indexes = {
                @Index(name = Currency.IX_CURRENCY_CURRENCY_CODE, columnList = "currencyCode")
        }
)
public class Currency implements Serializable {

    public static final String IX_CURRENCY_CURRENCY_CODE = "IX_CURRENCY_CURRENCY_CODE";
    protected static final String SEQ_NAME = "currencies_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "currencies_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    private static final long serialVersionUID = -1799428438852023627L;

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

    @NotNull
    @Column(nullable = false)
    private Integer fractionSize;

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

    public Integer getFractionSize() {
        return fractionSize;
    }

    public void setFractionSize(Integer fractionSize) {
        this.fractionSize = fractionSize;
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