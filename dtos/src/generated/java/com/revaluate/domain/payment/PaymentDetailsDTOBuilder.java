package com.revaluate.domain.payment;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class PaymentDetailsDTOBuilder
    implements Cloneable {
  protected PaymentDetailsDTOBuilder self;
  protected String value$firstName$java$lang$String;
  protected boolean isSet$firstName$java$lang$String;
  protected String value$lastName$java$lang$String;
  protected boolean isSet$lastName$java$lang$String;
  protected String value$paymentMethodNonce$java$lang$String;
  protected boolean isSet$paymentMethodNonce$java$lang$String;

  /**
   * Creates a new {@link PaymentDetailsDTOBuilder}.
   */
  public PaymentDetailsDTOBuilder() {
    self = (PaymentDetailsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentDetailsDTO#firstName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentDetailsDTOBuilder withFirstName(String value) {
    this.value$firstName$java$lang$String = value;
    this.isSet$firstName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentDetailsDTO#lastName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentDetailsDTOBuilder withLastName(String value) {
    this.value$lastName$java$lang$String = value;
    this.isSet$lastName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentDetailsDTO#paymentMethodNonce} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentDetailsDTOBuilder withPaymentMethodNonce(String value) {
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
      if (isSet$firstName$java$lang$String) {
        result.setFirstName(value$firstName$java$lang$String);
      }
      if (isSet$lastName$java$lang$String) {
        result.setLastName(value$lastName$java$lang$String);
      }
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
