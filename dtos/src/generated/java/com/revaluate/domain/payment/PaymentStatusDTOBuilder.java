package com.revaluate.domain.payment;

import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class PaymentStatusDTOBuilder
    implements Cloneable {
  protected PaymentStatusDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected String value$customerId$java$lang$String;
  protected boolean isSet$customerId$java$lang$String;
  protected String value$paymentMethodToken$java$lang$String;
  protected boolean isSet$paymentMethodToken$java$lang$String;
  protected LocalDateTime value$createdDate$org$joda$time$LocalDateTime;
  protected boolean isSet$createdDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$modifiedDate$org$joda$time$LocalDateTime;
  protected boolean isSet$modifiedDate$org$joda$time$LocalDateTime;

  /**
   * Creates a new {@link PaymentStatusDTOBuilder}.
   */
  public PaymentStatusDTOBuilder() {
    self = (PaymentStatusDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentStatusDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentStatusDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentStatusDTO#customerId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentStatusDTOBuilder withCustomerId(String value) {
    this.value$customerId$java$lang$String = value;
    this.isSet$customerId$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentStatusDTO#paymentMethodToken} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentStatusDTOBuilder withPaymentMethodToken(String value) {
    this.value$paymentMethodToken$java$lang$String = value;
    this.isSet$paymentMethodToken$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentStatusDTO#createdDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentStatusDTOBuilder withCreatedDate(LocalDateTime value) {
    this.value$createdDate$org$joda$time$LocalDateTime = value;
    this.isSet$createdDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentStatusDTO#modifiedDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentStatusDTOBuilder withModifiedDate(LocalDateTime value) {
    this.value$modifiedDate$org$joda$time$LocalDateTime = value;
    this.isSet$modifiedDate$org$joda$time$LocalDateTime = true;
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
      PaymentStatusDTOBuilder result = (PaymentStatusDTOBuilder)super.clone();
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
  public PaymentStatusDTOBuilder but() {
    return (PaymentStatusDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentStatusDTO} based on this builder's settings.
   *
   * @return the created PaymentStatusDTO
   */
  public PaymentStatusDTO build() {
    try {
      PaymentStatusDTO result = new PaymentStatusDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$customerId$java$lang$String) {
        result.setCustomerId(value$customerId$java$lang$String);
      }
      if (isSet$paymentMethodToken$java$lang$String) {
        result.setPaymentMethodToken(value$paymentMethodToken$java$lang$String);
      }
      if (isSet$createdDate$org$joda$time$LocalDateTime) {
        result.setCreatedDate(value$createdDate$org$joda$time$LocalDateTime);
      }
      if (isSet$modifiedDate$org$joda$time$LocalDateTime) {
        result.setModifiedDate(value$modifiedDate$org$joda$time$LocalDateTime);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
