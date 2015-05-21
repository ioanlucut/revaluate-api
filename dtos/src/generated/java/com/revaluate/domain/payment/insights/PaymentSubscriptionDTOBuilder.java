package com.revaluate.domain.payment.insights;

import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class PaymentSubscriptionDTOBuilder
    implements Cloneable {
  protected PaymentSubscriptionDTOBuilder self;
  protected String value$id$java$lang$String;
  protected boolean isSet$id$java$lang$String;
  protected double value$amount$double;
  protected boolean isSet$amount$double;
  protected String value$currencyCode$java$lang$String;
  protected boolean isSet$currencyCode$java$lang$String;
  protected String value$trialDuration$java$lang$String;
  protected boolean isSet$trialDuration$java$lang$String;
  protected String value$trialDurationUnit$java$lang$String;
  protected boolean isSet$trialDurationUnit$java$lang$String;
  protected String value$status$java$lang$String;
  protected boolean isSet$status$java$lang$String;
  protected LocalDateTime value$createdAt$org$joda$time$LocalDateTime;
  protected boolean isSet$createdAt$org$joda$time$LocalDateTime;
  protected LocalDateTime value$billingPeriodEndDate$org$joda$time$LocalDateTime;
  protected boolean isSet$billingPeriodEndDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$billingPeriodStartDate$org$joda$time$LocalDateTime;
  protected boolean isSet$billingPeriodStartDate$org$joda$time$LocalDateTime;

  /**
   * Creates a new {@link PaymentSubscriptionDTOBuilder}.
   */
  public PaymentSubscriptionDTOBuilder() {
    self = (PaymentSubscriptionDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentSubscriptionDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentSubscriptionDTOBuilder withId(String value) {
    this.value$id$java$lang$String = value;
    this.isSet$id$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentSubscriptionDTO#amount} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentSubscriptionDTOBuilder withAmount(double value) {
    this.value$amount$double = value;
    this.isSet$amount$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentSubscriptionDTO#currencyCode} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentSubscriptionDTOBuilder withCurrencyCode(String value) {
    this.value$currencyCode$java$lang$String = value;
    this.isSet$currencyCode$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentSubscriptionDTO#trialDuration} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentSubscriptionDTOBuilder withTrialDuration(String value) {
    this.value$trialDuration$java$lang$String = value;
    this.isSet$trialDuration$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentSubscriptionDTO#trialDurationUnit} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentSubscriptionDTOBuilder withTrialDurationUnit(String value) {
    this.value$trialDurationUnit$java$lang$String = value;
    this.isSet$trialDurationUnit$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentSubscriptionDTO#status} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentSubscriptionDTOBuilder withStatus(String value) {
    this.value$status$java$lang$String = value;
    this.isSet$status$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentSubscriptionDTO#createdAt} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentSubscriptionDTOBuilder withCreatedAt(LocalDateTime value) {
    this.value$createdAt$org$joda$time$LocalDateTime = value;
    this.isSet$createdAt$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentSubscriptionDTO#billingPeriodEndDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentSubscriptionDTOBuilder withBillingPeriodEndDate(LocalDateTime value) {
    this.value$billingPeriodEndDate$org$joda$time$LocalDateTime = value;
    this.isSet$billingPeriodEndDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentSubscriptionDTO#billingPeriodStartDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentSubscriptionDTOBuilder withBillingPeriodStartDate(LocalDateTime value) {
    this.value$billingPeriodStartDate$org$joda$time$LocalDateTime = value;
    this.isSet$billingPeriodStartDate$org$joda$time$LocalDateTime = true;
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
      PaymentSubscriptionDTOBuilder result = (PaymentSubscriptionDTOBuilder)super.clone();
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
  public PaymentSubscriptionDTOBuilder but() {
    return (PaymentSubscriptionDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentSubscriptionDTO} based on this builder's settings.
   *
   * @return the created PaymentSubscriptionDTO
   */
  public PaymentSubscriptionDTO build() {
    try {
      PaymentSubscriptionDTO result = new PaymentSubscriptionDTO();
      if (isSet$id$java$lang$String) {
        result.setId(value$id$java$lang$String);
      }
      if (isSet$amount$double) {
        result.setAmount(value$amount$double);
      }
      if (isSet$currencyCode$java$lang$String) {
        result.setCurrencyCode(value$currencyCode$java$lang$String);
      }
      if (isSet$trialDuration$java$lang$String) {
        result.setTrialDuration(value$trialDuration$java$lang$String);
      }
      if (isSet$trialDurationUnit$java$lang$String) {
        result.setTrialDurationUnit(value$trialDurationUnit$java$lang$String);
      }
      if (isSet$status$java$lang$String) {
        result.setStatus(value$status$java$lang$String);
      }
      if (isSet$createdAt$org$joda$time$LocalDateTime) {
        result.setCreatedAt(value$createdAt$org$joda$time$LocalDateTime);
      }
      if (isSet$billingPeriodEndDate$org$joda$time$LocalDateTime) {
        result.setBillingPeriodEndDate(value$billingPeriodEndDate$org$joda$time$LocalDateTime);
      }
      if (isSet$billingPeriodStartDate$org$joda$time$LocalDateTime) {
        result.setBillingPeriodStartDate(value$billingPeriodStartDate$org$joda$time$LocalDateTime);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
