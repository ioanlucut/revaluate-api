package com.revaluate.domain.payment.insights;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class PaymentCustomerDTOBuilder
    implements Cloneable {
  protected PaymentCustomerDTOBuilder self;
  protected String value$id$java$lang$String;
  protected boolean isSet$id$java$lang$String;
  protected String value$firstName$java$lang$String;
  protected boolean isSet$firstName$java$lang$String;
  protected String value$lastName$java$lang$String;
  protected boolean isSet$lastName$java$lang$String;
  protected String value$email$java$lang$String;
  protected boolean isSet$email$java$lang$String;

  /**
   * Creates a new {@link PaymentCustomerDTOBuilder}.
   */
  public PaymentCustomerDTOBuilder() {
    self = (PaymentCustomerDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentCustomerDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentCustomerDTOBuilder withId(String value) {
    this.value$id$java$lang$String = value;
    this.isSet$id$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentCustomerDTO#firstName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentCustomerDTOBuilder withFirstName(String value) {
    this.value$firstName$java$lang$String = value;
    this.isSet$firstName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentCustomerDTO#lastName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentCustomerDTOBuilder withLastName(String value) {
    this.value$lastName$java$lang$String = value;
    this.isSet$lastName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentCustomerDTO#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentCustomerDTOBuilder withEmail(String value) {
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
      PaymentCustomerDTOBuilder result = (PaymentCustomerDTOBuilder)super.clone();
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
  public PaymentCustomerDTOBuilder but() {
    return (PaymentCustomerDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentCustomerDTO} based on this builder's settings.
   *
   * @return the created PaymentCustomerDTO
   */
  public PaymentCustomerDTO build() {
    try {
      PaymentCustomerDTO result = new PaymentCustomerDTO();
      if (isSet$id$java$lang$String) {
        result.setId(value$id$java$lang$String);
      }
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
