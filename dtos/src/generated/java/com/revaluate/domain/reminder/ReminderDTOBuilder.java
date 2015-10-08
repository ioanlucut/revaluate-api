package com.revaluate.domain.reminder;

import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class ReminderDTOBuilder
    implements Cloneable {
  protected ReminderDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected LocalDateTime value$dueOnDate$org$joda$time$LocalDateTime;
  protected boolean isSet$dueOnDate$org$joda$time$LocalDateTime;
  protected String value$recurringRule$java$lang$String;
  protected boolean isSet$recurringRule$java$lang$String;
  protected LocalDateTime value$sentDate$org$joda$time$LocalDateTime;
  protected boolean isSet$sentDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$recurringStartDate$org$joda$time$LocalDateTime;
  protected boolean isSet$recurringStartDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$createdDate$org$joda$time$LocalDateTime;
  protected boolean isSet$createdDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$modifiedDate$org$joda$time$LocalDateTime;
  protected boolean isSet$modifiedDate$org$joda$time$LocalDateTime;
  protected int value$sentCount$int;
  protected boolean isSet$sentCount$int;
  protected ReminderType value$reminderType$com$revaluate$domain$reminder$ReminderType;
  protected boolean isSet$reminderType$com$revaluate$domain$reminder$ReminderType;

  /**
   * Creates a new {@link ReminderDTOBuilder}.
   */
  public ReminderDTOBuilder() {
    self = (ReminderDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ReminderDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ReminderDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ReminderDTO#dueOnDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ReminderDTOBuilder withDueOnDate(LocalDateTime value) {
    this.value$dueOnDate$org$joda$time$LocalDateTime = value;
    this.isSet$dueOnDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ReminderDTO#recurringRule} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ReminderDTOBuilder withRecurringRule(String value) {
    this.value$recurringRule$java$lang$String = value;
    this.isSet$recurringRule$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ReminderDTO#sentDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ReminderDTOBuilder withSentDate(LocalDateTime value) {
    this.value$sentDate$org$joda$time$LocalDateTime = value;
    this.isSet$sentDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ReminderDTO#recurringStartDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ReminderDTOBuilder withRecurringStartDate(LocalDateTime value) {
    this.value$recurringStartDate$org$joda$time$LocalDateTime = value;
    this.isSet$recurringStartDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ReminderDTO#createdDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ReminderDTOBuilder withCreatedDate(LocalDateTime value) {
    this.value$createdDate$org$joda$time$LocalDateTime = value;
    this.isSet$createdDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ReminderDTO#modifiedDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ReminderDTOBuilder withModifiedDate(LocalDateTime value) {
    this.value$modifiedDate$org$joda$time$LocalDateTime = value;
    this.isSet$modifiedDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ReminderDTO#sentCount} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ReminderDTOBuilder withSentCount(int value) {
    this.value$sentCount$int = value;
    this.isSet$sentCount$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ReminderDTO#reminderType} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ReminderDTOBuilder withReminderType(ReminderType value) {
    this.value$reminderType$com$revaluate$domain$reminder$ReminderType = value;
    this.isSet$reminderType$com$revaluate$domain$reminder$ReminderType = true;
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
      ReminderDTOBuilder result = (ReminderDTOBuilder)super.clone();
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
  public ReminderDTOBuilder but() {
    return (ReminderDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ReminderDTO} based on this builder's settings.
   *
   * @return the created ReminderDTO
   */
  public ReminderDTO build() {
    try {
      ReminderDTO result = new ReminderDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$dueOnDate$org$joda$time$LocalDateTime) {
        result.setDueOnDate(value$dueOnDate$org$joda$time$LocalDateTime);
      }
      if (isSet$recurringRule$java$lang$String) {
        result.setRecurringRule(value$recurringRule$java$lang$String);
      }
      if (isSet$sentDate$org$joda$time$LocalDateTime) {
        result.setSentDate(value$sentDate$org$joda$time$LocalDateTime);
      }
      if (isSet$recurringStartDate$org$joda$time$LocalDateTime) {
        result.setRecurringStartDate(value$recurringStartDate$org$joda$time$LocalDateTime);
      }
      if (isSet$createdDate$org$joda$time$LocalDateTime) {
        result.setCreatedDate(value$createdDate$org$joda$time$LocalDateTime);
      }
      if (isSet$modifiedDate$org$joda$time$LocalDateTime) {
        result.setModifiedDate(value$modifiedDate$org$joda$time$LocalDateTime);
      }
      if (isSet$sentCount$int) {
        result.setSentCount(value$sentCount$int);
      }
      if (isSet$reminderType$com$revaluate$domain$reminder$ReminderType) {
        result.setReminderType(value$reminderType$com$revaluate$domain$reminder$ReminderType);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
