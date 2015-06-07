package com.revaluate.domain.currency;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

@GeneratePojoBuilder
public class CurrencyDTO {

    @NotBlank
    private String currencyCode;

    private String displayName;
    private String symbol;
    private int numericCode;

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

    public int getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(int numericCode) {
        this.numericCode = numericCode;
    }

    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "currencyCode='" + currencyCode + '\'' +
                ", displayName='" + displayName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", numericCode=" + numericCode +
                '}';
    }
}