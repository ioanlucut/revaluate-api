package com.revaluate.domain.payment.insights;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

@GeneratePojoBuilder
public class PaymentMethodDTO {

    @NotBlank
    private String bin;

    @NotBlank
    private String cardType;

    @NotBlank
    private String expirationMonth;

    @NotBlank
    private String expirationYear;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String last4;

    private List<PaymentSubscriptionDTO> paymentSubscriptionDTOList;

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public List<PaymentSubscriptionDTO> getPaymentSubscriptionDTOList() {
        return paymentSubscriptionDTOList;
    }

    public void setPaymentSubscriptionDTOList(List<PaymentSubscriptionDTO> paymentSubscriptionDTOList) {
        this.paymentSubscriptionDTOList = paymentSubscriptionDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethodDTO that = (PaymentMethodDTO) o;
        return Objects.equals(bin, that.bin) &&
                Objects.equals(cardType, that.cardType) &&
                Objects.equals(expirationMonth, that.expirationMonth) &&
                Objects.equals(expirationYear, that.expirationYear) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(last4, that.last4) &&
                Objects.equals(paymentSubscriptionDTOList, that.paymentSubscriptionDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bin, cardType, expirationMonth, expirationYear, imageUrl, last4, paymentSubscriptionDTOList);
    }

    @Override
    public String toString() {
        return "PaymentMethodDTO{" +
                "bin='" + bin + '\'' +
                ", cardType='" + cardType + '\'' +
                ", expirationMonth='" + expirationMonth + '\'' +
                ", expirationYear='" + expirationYear + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", last4='" + last4 + '\'' +
                ", paymentSubscriptionDTOList=" + paymentSubscriptionDTOList +
                '}';
    }
}