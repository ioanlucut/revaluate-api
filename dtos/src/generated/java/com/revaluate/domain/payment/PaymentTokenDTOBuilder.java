package com.revaluate.domain.payment;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class PaymentTokenDTOBuilder
    implements Cloneable {
  protected PaymentTokenDTOBuilder self;
  protected String value$clientToken$java$lang$String;
  protected boolean isSet$clientToken$java$lang$String;

  /**
   * Creates a new {@link PaymentTokenDTOBuilder}.
   */
  public PaymentTokenDTOBuilder() {
    self = (PaymentTokenDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentTokenDTO#clientToken} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentTokenDTOBuilder withClientToken(String value) {
    this.value$clientToken$java$lang$String = value;
    this.isSet$clientToken$java$lang$String = true;
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
      PaymentTokenDTOBuilder result = (PaymentTokenDTOBuilder)super.clone();
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
  public PaymentTokenDTOBuilder but() {
    return (PaymentTokenDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentTokenDTO} based on this builder's settings.
   *
   * @return the created PaymentTokenDTO
   */
  public PaymentTokenDTO build() {
    try {
      PaymentTokenDTO result = new PaymentTokenDTO();
      if (isSet$clientToken$java$lang$String) {
        result.setClientToken(value$clientToken$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
