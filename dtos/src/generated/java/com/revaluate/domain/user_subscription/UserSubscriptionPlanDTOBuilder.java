package com.revaluate.domain.user_subscription;

import com.revaluate.domain.subscription_plan.SubscriptionPlanDTO;
import com.revaluate.domain.subscription_plan.SubscriptionType;
import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class UserSubscriptionPlanDTOBuilder
    implements Cloneable {
  protected UserSubscriptionPlanDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected SubscriptionPlanDTO value$subscriptionPlanDTO$com$revaluate$domain$subscription_plan$SubscriptionPlanDTO;
  protected boolean isSet$subscriptionPlanDTO$com$revaluate$domain$subscription_plan$SubscriptionPlanDTO;
  protected SubscriptionType value$subscriptionType$com$revaluate$domain$subscription_plan$SubscriptionType;
  protected boolean isSet$subscriptionType$com$revaluate$domain$subscription_plan$SubscriptionType;
  protected LocalDateTime value$createdDate$org$joda$time$LocalDateTime;
  protected boolean isSet$createdDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$modifiedDate$org$joda$time$LocalDateTime;
  protected boolean isSet$modifiedDate$org$joda$time$LocalDateTime;

  /**
   * Creates a new {@link UserSubscriptionPlanDTOBuilder}.
   */
  public UserSubscriptionPlanDTOBuilder() {
    self = (UserSubscriptionPlanDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link UserSubscriptionPlanDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserSubscriptionPlanDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserSubscriptionPlanDTO#subscriptionPlanDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserSubscriptionPlanDTOBuilder withSubscriptionPlanDTO(SubscriptionPlanDTO value) {
    this.value$subscriptionPlanDTO$com$revaluate$domain$subscription_plan$SubscriptionPlanDTO = value;
    this.isSet$subscriptionPlanDTO$com$revaluate$domain$subscription_plan$SubscriptionPlanDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserSubscriptionPlanDTO#subscriptionType} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserSubscriptionPlanDTOBuilder withSubscriptionType(SubscriptionType value) {
    this.value$subscriptionType$com$revaluate$domain$subscription_plan$SubscriptionType = value;
    this.isSet$subscriptionType$com$revaluate$domain$subscription_plan$SubscriptionType = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserSubscriptionPlanDTO#createdDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserSubscriptionPlanDTOBuilder withCreatedDate(LocalDateTime value) {
    this.value$createdDate$org$joda$time$LocalDateTime = value;
    this.isSet$createdDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserSubscriptionPlanDTO#modifiedDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserSubscriptionPlanDTOBuilder withModifiedDate(LocalDateTime value) {
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
      UserSubscriptionPlanDTOBuilder result = (UserSubscriptionPlanDTOBuilder)super.clone();
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
  public UserSubscriptionPlanDTOBuilder but() {
    return (UserSubscriptionPlanDTOBuilder)clone();
  }

  /**
   * Creates a new {@link UserSubscriptionPlanDTO} based on this builder's settings.
   *
   * @return the created UserSubscriptionPlanDTO
   */
  public UserSubscriptionPlanDTO build() {
    try {
      UserSubscriptionPlanDTO result = new UserSubscriptionPlanDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$subscriptionPlanDTO$com$revaluate$domain$subscription_plan$SubscriptionPlanDTO) {
        result.setSubscriptionPlanDTO(value$subscriptionPlanDTO$com$revaluate$domain$subscription_plan$SubscriptionPlanDTO);
      }
      if (isSet$subscriptionType$com$revaluate$domain$subscription_plan$SubscriptionType) {
        result.setSubscriptionType(value$subscriptionType$com$revaluate$domain$subscription_plan$SubscriptionType);
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
