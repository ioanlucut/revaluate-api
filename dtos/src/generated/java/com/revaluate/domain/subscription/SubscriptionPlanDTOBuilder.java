package com.revaluate.domain.subscription;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class SubscriptionPlanDTOBuilder
    implements Cloneable {
  protected SubscriptionPlanDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected double value$value$double;
  protected boolean isSet$value$double;
  protected String value$description$java$lang$String;
  protected boolean isSet$description$java$lang$String;
  protected SubscriptionType value$subscriptionType$com$revaluate$domain$subscription$SubscriptionType;
  protected boolean isSet$subscriptionType$com$revaluate$domain$subscription$SubscriptionType;

  /**
   * Creates a new {@link SubscriptionPlanDTOBuilder}.
   */
  public SubscriptionPlanDTOBuilder() {
    self = (SubscriptionPlanDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link SubscriptionPlanDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SubscriptionPlanDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SubscriptionPlanDTO#value} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SubscriptionPlanDTOBuilder withValue(double value) {
    this.value$value$double = value;
    this.isSet$value$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SubscriptionPlanDTO#description} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SubscriptionPlanDTOBuilder withDescription(String value) {
    this.value$description$java$lang$String = value;
    this.isSet$description$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SubscriptionPlanDTO#subscriptionType} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SubscriptionPlanDTOBuilder withSubscriptionType(SubscriptionType value) {
    this.value$subscriptionType$com$revaluate$domain$subscription$SubscriptionType = value;
    this.isSet$subscriptionType$com$revaluate$domain$subscription$SubscriptionType = true;
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
      SubscriptionPlanDTOBuilder result = (SubscriptionPlanDTOBuilder)super.clone();
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
  public SubscriptionPlanDTOBuilder but() {
    return (SubscriptionPlanDTOBuilder)clone();
  }

  /**
   * Creates a new {@link SubscriptionPlanDTO} based on this builder's settings.
   *
   * @return the created SubscriptionPlanDTO
   */
  public SubscriptionPlanDTO build() {
    try {
      SubscriptionPlanDTO result = new SubscriptionPlanDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$value$double) {
        result.setValue(value$value$double);
      }
      if (isSet$description$java$lang$String) {
        result.setDescription(value$description$java$lang$String);
      }
      if (isSet$subscriptionType$com$revaluate$domain$subscription$SubscriptionType) {
        result.setSubscriptionType(value$subscriptionType$com$revaluate$domain$subscription$SubscriptionType);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
