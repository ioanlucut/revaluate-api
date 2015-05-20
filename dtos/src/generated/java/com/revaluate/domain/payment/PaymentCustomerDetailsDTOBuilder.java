package com.revaluate.domain.payment;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class PaymentCustomerDetailsDTOBuilder
    implements Cloneable {
  protected PaymentCustomerDetailsDTOBuilder self;
  protected String value$firstName$java$lang$String;
  protected boolean isSet$firstName$java$lang$String;
  protected String value$lastName$java$lang$String;
  protected boolean isSet$lastName$java$lang$String;
  protected String value$email$java$lang$String;
  protected boolean isSet$email$java$lang$String;

  /**
   * Creates a new {@link PaymentCustomerDetailsDTOBuilder}.
   */
  public PaymentCustomerDetailsDTOBuilder() {
    self = (PaymentCustomerDetailsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentCustomerDetailsDTO#firstName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentCustomerDetailsDTOBuilder withFirstName(String value) {
    this.value$firstName$java$lang$String = value;
    this.isSet$firstName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentCustomerDetailsDTO#lastName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentCustomerDetailsDTOBuilder withLastName(String value) {
    this.value$lastName$java$lang$String = value;
    this.isSet$lastName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentCustomerDetailsDTO#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentCustomerDetailsDTOBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
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
      PaymentCustomerDetailsDTOBuilder result = (PaymentCustomerDetailsDTOBuilder)super.clone();
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
  public PaymentCustomerDetailsDTOBuilder but() {
    return (PaymentCustomerDetailsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentCustomerDetailsDTO} based on this builder's settings.
   *
   * @return the created PaymentCustomerDetailsDTO
   */
  public PaymentCustomerDetailsDTO build() {
    try {
      PaymentCustomerDetailsDTO result = new PaymentCustomerDetailsDTO();
      if (isSet$firstName$java$lang$String) {
        result.setFirstName(value$firstName$java$lang$String);
      }
      if (isSet$lastName$java$lang$String) {
        result.setLastName(value$lastName$java$lang$String);
      }
      if (isSet$email$java$lang$String) {
        result.setEmail(value$email$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
