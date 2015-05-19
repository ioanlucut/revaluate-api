package com.revaluate.domain.payment;

import javax.annotation.Generated;

import com.revaluate.domain.payment.insights.PaymentInsightsDTO;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class PaymentInsightsDTOBuilder
    implements Cloneable {
  protected PaymentInsightsDTOBuilder self;
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
   * Creates a new {@link PaymentInsightsDTOBuilder}.
   */
  public PaymentInsightsDTOBuilder() {
    self = (PaymentInsightsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link PaymentInsightsDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentInsightsDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentInsightsDTO#customerId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentInsightsDTOBuilder withCustomerId(String value) {
    this.value$customerId$java$lang$String = value;
    this.isSet$customerId$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentInsightsDTO#paymentMethodToken} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentInsightsDTOBuilder withPaymentMethodToken(String value) {
    this.value$paymentMethodToken$java$lang$String = value;
    this.isSet$paymentMethodToken$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentInsightsDTO#createdDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentInsightsDTOBuilder withCreatedDate(LocalDateTime value) {
    this.value$createdDate$org$joda$time$LocalDateTime = value;
    this.isSet$createdDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link PaymentInsightsDTO#modifiedDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PaymentInsightsDTOBuilder withModifiedDate(LocalDateTime value) {
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
      PaymentInsightsDTOBuilder result = (PaymentInsightsDTOBuilder)super.clone();
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
  public PaymentInsightsDTOBuilder but() {
    return (PaymentInsightsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link PaymentInsightsDTO} based on this builder's settings.
   *
   * @return the created PaymentInsightsDTO
   */
  public PaymentInsightsDTO build() {
    try {
      PaymentInsightsDTO result = new PaymentInsightsDTO();
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
