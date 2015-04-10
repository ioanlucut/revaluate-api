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

    private String displayName;
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
                ", numericCode=" + numericCode +
                '}';
    }
}