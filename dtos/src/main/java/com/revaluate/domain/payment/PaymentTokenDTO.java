package com.revaluate.domain.payment;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

@GeneratePojoBuilder
public class PaymentTokenDTO {

    @NotEmpty
    private String clientToken;

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentTokenDTO that = (PaymentTokenDTO) o;
        return Objects.equals(clientToken, that.clientToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientToken);
    }

    @Override
    public String toString() {
        return "PaymentTokenDTO{" +
                "clientToken='" + clientToken + '\'' +
                '}';
    }
}