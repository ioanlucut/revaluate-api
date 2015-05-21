package com.revaluate.domain.payment.insights;

import java.util.List;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class PaymentInsightsDTOBuilder
    implements Cloneable {
  protected PaymentInsightsDTOBuilder self;
  protected PaymentCustomerDTO value$paymentCustomerDTO$com$revaluate$domain$payment$insights$PaymentCustomerDTO;
  protected boolean isSet$paymentCustomerDTO$com$revaluate$domain$payment$insights$PaymentCustomerDTO;
  protected List<PaymentMethodDTO> value$paymentMethodDTOs$java$util$List;
  protected boolean isSet$paymentMethodDTOs$java$util$List;
  protected List<PaymentTransactionDTO> value$paymentTransactionDTOs$java$util$List;
  protected boolean isSet$paymentTransactionDTOs$java$util$List;
  protected boolean value$subscriptionActive$boolean;
  protected boolean isSet$subscriptionActive$boolean;

  /**
   * Creates a new {@link PaymentInsightsDTOBuilder}.
   */
  public PaymentInsightsDTOBuilder() {
    self = (PaymentInsightsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentInsightsDTO#paymentCustomerDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentInsightsDTOBuilder withPaymentCustomerDTO(PaymentCustomerDTO value) {
    this.value$paymentCustomerDTO$com$revaluate$domain$payment$insights$PaymentCustomerDTO = value;
    this.isSet$paymentCustomerDTO$com$revaluate$domain$payment$insights$PaymentCustomerDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentInsightsDTO#paymentMethodDTOs} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentInsightsDTOBuilder withPaymentMethodDTOs(List<PaymentMethodDTO> value) {
    this.value$paymentMethodDTOs$java$util$List = value;
    this.isSet$paymentMethodDTOs$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentInsightsDTO#paymentTransactionDTOs} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentInsightsDTOBuilder withPaymentTransactionDTOs(List<PaymentTransactionDTO> value) {
    this.value$paymentTransactionDTOs$java$util$List = value;
    this.isSet$paymentTransactionDTOs$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentInsightsDTO#subscriptionActive} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentInsightsDTOBuilder withSubscriptionActive(boolean value) {
    this.value$subscriptionActive$boolean = value;
    this.isSet$subscriptionActive$boolean = true;
    return self;
  }

  /**
   * Returns a clone of this builder.
   *
   * @return the clone
   */
  @Override
  public Object clone() {
    try {
      PaymentInsightsDTOBuilder result = (PaymentInsightsDTOBuilder)super.clone();
      result.self = result;
      return result;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.getMessage());
    }
  }

  /**
   * Returns a clone of this builder.
   *
   * @return the clone
   */
  public PaymentInsightsDTOBuilder but() {
    return (PaymentInsightsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentInsightsDTO} based on this builder's settings.
   *
   * @return the created PaymentInsightsDTO
   */
  public PaymentInsightsDTO build() {
    try {
      PaymentInsightsDTO result = new PaymentInsightsDTO();
      if (isSet$paymentCustomerDTO$com$revaluate$domain$payment$insights$PaymentCustomerDTO) {
        result.setPaymentCustomerDTO(value$paymentCustomerDTO$com$revaluate$domain$payment$insights$PaymentCustomerDTO);
      }
      if (isSet$paymentMethodDTOs$java$util$List) {
        result.setPaymentMethodDTOs(value$paymentMethodDTOs$java$util$List);
      }
      if (isSet$paymentTransactionDTOs$java$util$List) {
        result.setPaymentTransactionDTOs(value$paymentTransactionDTOs$java$util$List);
      }
      if (isSet$subscriptionActive$boolean) {
        result.setSubscriptionActive(value$subscriptionActive$boolean);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
