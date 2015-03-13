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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}