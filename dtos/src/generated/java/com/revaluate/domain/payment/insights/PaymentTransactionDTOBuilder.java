package com.revaluate.domain.payment.insights;

import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class PaymentTransactionDTOBuilder
    implements Cloneable {
  protected PaymentTransactionDTOBuilder self;
  protected String value$id$java$lang$String;
  protected boolean isSet$id$java$lang$String;
  protected double value$amount$double;
  protected boolean isSet$amount$double;
  protected String value$currencyCode$java$lang$String;
  protected boolean isSet$currencyCode$java$lang$String;
  protected boolean value$recurring$boolean;
  protected boolean isSet$recurring$boolean;
  protected String value$status$java$lang$String;
  protected boolean isSet$status$java$lang$String;
  protected LocalDateTime value$createdAt$org$joda$time$LocalDateTime;
  protected boolean isSet$createdAt$org$joda$time$LocalDateTime;

  /**
   * Creates a new {@link PaymentTransactionDTOBuilder}.
   */
  public PaymentTransactionDTOBuilder() {
    self = (PaymentTransactionDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentTransactionDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentTransactionDTOBuilder withId(String value) {
    this.value$id$java$lang$String = value;
    this.isSet$id$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentTransactionDTO#amount} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentTransactionDTOBuilder withAmount(double value) {
    this.value$amount$double = value;
    this.isSet$amount$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentTransactionDTO#currencyCode} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentTransactionDTOBuilder withCurrencyCode(String value) {
    this.value$currencyCode$java$lang$String = value;
    this.isSet$currencyCode$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentTransactionDTO#recurring} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentTransactionDTOBuilder withRecurring(boolean value) {
    this.value$recurring$boolean = value;
    this.isSet$recurring$boolean = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentTransactionDTO#status} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentTransactionDTOBuilder withStatus(String value) {
    this.value$status$java$lang$String = value;
    this.isSet$status$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentTransactionDTO#createdAt} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentTransactionDTOBuilder withCreatedAt(LocalDateTime value) {
    this.value$createdAt$org$joda$time$LocalDateTime = value;
    this.isSet$createdAt$org$joda$time$LocalDateTime = true;
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
      PaymentTransactionDTOBuilder result = (PaymentTransactionDTOBuilder)super.clone();
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
  public PaymentTransactionDTOBuilder but() {
    return (PaymentTransactionDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentTransactionDTO} based on this builder's settings.
   *
   * @return the created PaymentTransactionDTO
   */
  public PaymentTransactionDTO build() {
    try {
      PaymentTransactionDTO result = new PaymentTransactionDTO();
      if (isSet$id$java$lang$String) {
        result.setId(value$id$java$lang$String);
      }
      if (isSet$amount$double) {
        result.setAmount(value$amount$double);
      }
      if (isSet$currencyCode$java$lang$String) {
        result.setCurrencyCode(value$currencyCode$java$lang$String);
      }
      if (isSet$recurring$boolean) {
        result.setRecurring(value$recurring$boolean);
      }
      if (isSet$status$java$lang$String) {
        result.setStatus(value$status$java$lang$String);
      }
      if (isSet$createdAt$org$joda$time$LocalDateTime) {
        result.setCreatedAt(value$createdAt$org$joda$time$LocalDateTime);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
