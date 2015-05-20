package com.revaluate.domain.payment;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class PaymentNonceDetailsDTOBuilder
    implements Cloneable {
  protected PaymentNonceDetailsDTOBuilder self;
  protected String value$paymentMethodNonce$java$lang$String;
  protected boolean isSet$paymentMethodNonce$java$lang$String;

  /**
   * Creates a new {@link PaymentNonceDetailsDTOBuilder}.
   */
  public PaymentNonceDetailsDTOBuilder() {
    self = (PaymentNonceDetailsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentNonceDetailsDTO#paymentMethodNonce} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentNonceDetailsDTOBuilder withPaymentMethodNonce(String value) {
    this.value$paymentMethodNonce$java$lang$String = value;
    this.isSet$paymentMethodNonce$java$lang$String = true;
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
      PaymentNonceDetailsDTOBuilder result = (PaymentNonceDetailsDTOBuilder)super.clone();
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
  public PaymentNonceDetailsDTOBuilder but() {
    return (PaymentNonceDetailsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentNonceDetailsDTO} based on this builder's settings.
   *
   * @return the created PaymentNonceDetailsDTO
   */
  public PaymentNonceDetailsDTO build() {
    try {
      PaymentNonceDetailsDTO result = new PaymentNonceDetailsDTO();
      if (isSet$paymentMethodNonce$java$lang$String) {
        result.setPaymentMethodNonce(value$paymentMethodNonce$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
