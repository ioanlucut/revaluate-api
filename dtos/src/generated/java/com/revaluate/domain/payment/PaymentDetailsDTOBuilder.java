package com.revaluate.domain.payment;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class PaymentDetailsDTOBuilder
    implements Cloneable {
  protected PaymentDetailsDTOBuilder self;
  protected PaymentCustomerDetailsDTO value$paymentCustomerDetailsDTO$com$revaluate$domain$payment$PaymentCustomerDetailsDTO;
  protected boolean isSet$paymentCustomerDetailsDTO$com$revaluate$domain$payment$PaymentCustomerDetailsDTO;
  protected PaymentNonceDetailsDTO value$paymentNonceDetailsDTO$com$revaluate$domain$payment$PaymentNonceDetailsDTO;
  protected boolean isSet$paymentNonceDetailsDTO$com$revaluate$domain$payment$PaymentNonceDetailsDTO;

  /**
   * Creates a new {@link PaymentDetailsDTOBuilder}.
   */
  public PaymentDetailsDTOBuilder() {
    self = (PaymentDetailsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentDetailsDTO#paymentCustomerDetailsDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentDetailsDTOBuilder withPaymentCustomerDetailsDTO(PaymentCustomerDetailsDTO value) {
    this.value$paymentCustomerDetailsDTO$com$revaluate$domain$payment$PaymentCustomerDetailsDTO = value;
    this.isSet$paymentCustomerDetailsDTO$com$revaluate$domain$payment$PaymentCustomerDetailsDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentDetailsDTO#paymentNonceDetailsDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentDetailsDTOBuilder withPaymentNonceDetailsDTO(PaymentNonceDetailsDTO value) {
    this.value$paymentNonceDetailsDTO$com$revaluate$domain$payment$PaymentNonceDetailsDTO = value;
    this.isSet$paymentNonceDetailsDTO$com$revaluate$domain$payment$PaymentNonceDetailsDTO = true;
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
      PaymentDetailsDTOBuilder result = (PaymentDetailsDTOBuilder)super.clone();
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
  public PaymentDetailsDTOBuilder but() {
    return (PaymentDetailsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentDetailsDTO} based on this builder's settings.
   *
   * @return the created PaymentDetailsDTO
   */
  public PaymentDetailsDTO build() {
    try {
      PaymentDetailsDTO result = new PaymentDetailsDTO();
      if (isSet$paymentCustomerDetailsDTO$com$revaluate$domain$payment$PaymentCustomerDetailsDTO) {
        result.setPaymentCustomerDetailsDTO(value$paymentCustomerDetailsDTO$com$revaluate$domain$payment$PaymentCustomerDetailsDTO);
      }
      if (isSet$paymentNonceDetailsDTO$com$revaluate$domain$payment$PaymentNonceDetailsDTO) {
        result.setPaymentNonceDetailsDTO(value$paymentNonceDetailsDTO$com$revaluate$domain$payment$PaymentNonceDetailsDTO);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
