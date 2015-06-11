package com.revaluate.domain.currency;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.views.Views;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

@GeneratePojoBuilder
public class CurrencyDTO {

    @NotBlank
    @JsonView({Views.StrictView.class})
    private String currencyCode;

    @JsonView({Views.StrictView.class})
    private String displayName;

    @JsonView({Views.StrictView.class})
    private String symbol;

    @JsonView({Views.StrictView.class})
    private int numericCode;

    @JsonView({Views.StrictView.class})
    private int fractionSize;

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

    public int getFractionSize() {
        return fractionSize;
    }

    public void setFractionSize(int fractionSize) {
        this.fractionSize = fractionSize;
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