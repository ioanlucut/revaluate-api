package com.revaluate.domain.payment.insights;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class PaymentMethodDTOBuilder
    implements Cloneable {
  protected PaymentMethodDTOBuilder self;
  protected String value$bin$java$lang$String;
  protected boolean isSet$bin$java$lang$String;
  protected String value$cardType$java$lang$String;
  protected boolean isSet$cardType$java$lang$String;
  protected String value$expirationMonth$java$lang$String;
  protected boolean isSet$expirationMonth$java$lang$String;
  protected String value$expirationYear$java$lang$String;
  protected boolean isSet$expirationYear$java$lang$String;
  protected String value$imageUrl$java$lang$String;
  protected boolean isSet$imageUrl$java$lang$String;
  protected String value$last4$java$lang$String;
  protected boolean isSet$last4$java$lang$String;

  /**
   * Creates a new {@link PaymentMethodDTOBuilder}.
   */
  public PaymentMethodDTOBuilder() {
    self = (PaymentMethodDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentMethodDTO#bin} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentMethodDTOBuilder withBin(String value) {
    this.value$bin$java$lang$String = value;
    this.isSet$bin$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentMethodDTO#cardType} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentMethodDTOBuilder withCardType(String value) {
    this.value$cardType$java$lang$String = value;
    this.isSet$cardType$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentMethodDTO#expirationMonth} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentMethodDTOBuilder withExpirationMonth(String value) {
    this.value$expirationMonth$java$lang$String = value;
    this.isSet$expirationMonth$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentMethodDTO#expirationYear} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentMethodDTOBuilder withExpirationYear(String value) {
    this.value$expirationYear$java$lang$String = value;
    this.isSet$expirationYear$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentMethodDTO#imageUrl} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentMethodDTOBuilder withImageUrl(String value) {
    this.value$imageUrl$java$lang$String = value;
    this.isSet$imageUrl$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentMethodDTO#last4} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentMethodDTOBuilder withLast4(String value) {
    this.value$last4$java$lang$String = value;
    this.isSet$last4$java$lang$String = true;
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
      PaymentMethodDTOBuilder result = (PaymentMethodDTOBuilder)super.clone();
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
  public PaymentMethodDTOBuilder but() {
    return (PaymentMethodDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentMethodDTO} based on this builder's settings.
   *
   * @return the created PaymentMethodDTO
   */
  public PaymentMethodDTO build() {
    try {
      PaymentMethodDTO result = new PaymentMethodDTO();
      if (isSet$bin$java$lang$String) {
        result.setBin(value$bin$java$lang$String);
      }
      if (isSet$cardType$java$lang$String) {
        result.setCardType(value$cardType$java$lang$String);
      }
      if (isSet$expirationMonth$java$lang$String) {
        result.setExpirationMonth(value$expirationMonth$java$lang$String);
      }
      if (isSet$expirationYear$java$lang$String) {
        result.setExpirationYear(value$expirationYear$java$lang$String);
      }
      if (isSet$imageUrl$java$lang$String) {
        result.setImageUrl(value$imageUrl$java$lang$String);
      }
      if (isSet$last4$java$lang$String) {
        result.setLast4(value$last4$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
