package com.revaluate.domain.account;

import com.revaluate.domain.currency.CurrencyDTO;
import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class UserDTOBuilder
    implements Cloneable {
  protected UserDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected String value$firstName$java$lang$String;
  protected boolean isSet$firstName$java$lang$String;
  protected String value$lastName$java$lang$String;
  protected boolean isSet$lastName$java$lang$String;
  protected String value$email$java$lang$String;
  protected boolean isSet$email$java$lang$String;
  protected String value$password$java$lang$String;
  protected boolean isSet$password$java$lang$String;
  protected CurrencyDTO value$currency$com$revaluate$domain$currency$CurrencyDTO;
  protected boolean isSet$currency$com$revaluate$domain$currency$CurrencyDTO;
  protected boolean value$initiated$boolean;
  protected boolean isSet$initiated$boolean;
  protected boolean value$emailConfirmed$boolean;
  protected boolean isSet$emailConfirmed$boolean;
  protected LocalDateTime value$createdDate$org$joda$time$LocalDateTime;
  protected boolean isSet$createdDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$modifiedDate$org$joda$time$LocalDateTime;
  protected boolean isSet$modifiedDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$endTrialDate$org$joda$time$LocalDateTime;
  protected boolean isSet$endTrialDate$org$joda$time$LocalDateTime;
  protected UserSubscriptionStatus value$userSubscriptionStatus$com$revaluate$domain$account$UserSubscriptionStatus;
  protected boolean isSet$userSubscriptionStatus$com$revaluate$domain$account$UserSubscriptionStatus;

  /**
   * Creates a new {@link UserDTOBuilder}.
   */
  public UserDTOBuilder() {
    self = (UserDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link UserDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#firstName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withFirstName(String value) {
    this.value$firstName$java$lang$String = value;
    this.isSet$firstName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#lastName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withLastName(String value) {
    this.value$lastName$java$lang$String = value;
    this.isSet$lastName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#password} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withPassword(String value) {
    this.value$password$java$lang$String = value;
    this.isSet$password$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#currency} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withCurrency(CurrencyDTO value) {
    this.value$currency$com$revaluate$domain$currency$CurrencyDTO = value;
    this.isSet$currency$com$revaluate$domain$currency$CurrencyDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#initiated} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withInitiated(boolean value) {
    this.value$initiated$boolean = value;
    this.isSet$initiated$boolean = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#emailConfirmed} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withEmailConfirmed(boolean value) {
    this.value$emailConfirmed$boolean = value;
    this.isSet$emailConfirmed$boolean = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#createdDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withCreatedDate(LocalDateTime value) {
    this.value$createdDate$org$joda$time$LocalDateTime = value;
    this.isSet$createdDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#modifiedDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withModifiedDate(LocalDateTime value) {
    this.value$modifiedDate$org$joda$time$LocalDateTime = value;
    this.isSet$modifiedDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#endTrialDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withEndTrialDate(LocalDateTime value) {
    this.value$endTrialDate$org$joda$time$LocalDateTime = value;
    this.isSet$endTrialDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#userSubscriptionStatus} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withUserSubscriptionStatus(UserSubscriptionStatus value) {
    this.value$userSubscriptionStatus$com$revaluate$domain$account$UserSubscriptionStatus = value;
    this.isSet$userSubscriptionStatus$com$revaluate$domain$account$UserSubscriptionStatus = true;
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
      UserDTOBuilder result = (UserDTOBuilder)super.clone();
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
  public UserDTOBuilder but() {
    return (UserDTOBuilder)clone();
  }

  /**
   * Creates a new {@link UserDTO} based on this builder's settings.
   *
   * @return the created UserDTO
   */
  public UserDTO build() {
    try {
      UserDTO result = new UserDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
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
      if (isSet$password$java$lang$String) {
        result.setPassword(value$password$java$lang$String);
      }
      if (isSet$currency$com$revaluate$domain$currency$CurrencyDTO) {
        result.setCurrency(value$currency$com$revaluate$domain$currency$CurrencyDTO);
      }
      if (isSet$initiated$boolean) {
        result.setInitiated(value$initiated$boolean);
      }
      if (isSet$emailConfirmed$boolean) {
        result.setEmailConfirmed(value$emailConfirmed$boolean);
      }
      if (isSet$createdDate$org$joda$time$LocalDateTime) {
        result.setCreatedDate(value$createdDate$org$joda$time$LocalDateTime);
      }
      if (isSet$modifiedDate$org$joda$time$LocalDateTime) {
        result.setModifiedDate(value$modifiedDate$org$joda$time$LocalDateTime);
      }
      if (isSet$endTrialDate$org$joda$time$LocalDateTime) {
        result.setEndTrialDate(value$endTrialDate$org$joda$time$LocalDateTime);
      }
      if (isSet$userSubscriptionStatus$com$revaluate$domain$account$UserSubscriptionStatus) {
        result.setUserSubscriptionStatus(value$userSubscriptionStatus$com$revaluate$domain$account$UserSubscriptionStatus);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
