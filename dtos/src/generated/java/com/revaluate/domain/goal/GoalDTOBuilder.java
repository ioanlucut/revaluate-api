package com.revaluate.domain.goal;

import com.revaluate.domain.category.CategoryDTO;
import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class GoalDTOBuilder
    implements Cloneable {
  protected GoalDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected double value$value$double;
  protected boolean isSet$value$double;
  protected GoalTarget value$goalTarget$com$revaluate$domain$goal$GoalTarget;
  protected boolean isSet$goalTarget$com$revaluate$domain$goal$GoalTarget;
  protected CategoryDTO value$category$com$revaluate$domain$category$CategoryDTO;
  protected boolean isSet$category$com$revaluate$domain$category$CategoryDTO;
  protected LocalDateTime value$startDate$org$joda$time$LocalDateTime;
  protected boolean isSet$startDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$endDate$org$joda$time$LocalDateTime;
  protected boolean isSet$endDate$org$joda$time$LocalDateTime;
  protected GoalStatusDTO value$goalStatusDTO$com$revaluate$domain$goal$GoalStatusDTO;
  protected boolean isSet$goalStatusDTO$com$revaluate$domain$goal$GoalStatusDTO;
  protected LocalDateTime value$createdDate$org$joda$time$LocalDateTime;
  protected boolean isSet$createdDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$modifiedDate$org$joda$time$LocalDateTime;
  protected boolean isSet$modifiedDate$org$joda$time$LocalDateTime;

  /**
   * Creates a new {@link GoalDTOBuilder}.
   */
  public GoalDTOBuilder() {
    self = (GoalDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link GoalDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GoalDTO#value} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalDTOBuilder withValue(double value) {
    this.value$value$double = value;
    this.isSet$value$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GoalDTO#goalTarget} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalDTOBuilder withGoalTarget(GoalTarget value) {
    this.value$goalTarget$com$revaluate$domain$goal$GoalTarget = value;
    this.isSet$goalTarget$com$revaluate$domain$goal$GoalTarget = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GoalDTO#category} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalDTOBuilder withCategory(CategoryDTO value) {
    this.value$category$com$revaluate$domain$category$CategoryDTO = value;
    this.isSet$category$com$revaluate$domain$category$CategoryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GoalDTO#startDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalDTOBuilder withStartDate(LocalDateTime value) {
    this.value$startDate$org$joda$time$LocalDateTime = value;
    this.isSet$startDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GoalDTO#endDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalDTOBuilder withEndDate(LocalDateTime value) {
    this.value$endDate$org$joda$time$LocalDateTime = value;
    this.isSet$endDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GoalDTO#goalStatusDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalDTOBuilder withGoalStatusDTO(GoalStatusDTO value) {
    this.value$goalStatusDTO$com$revaluate$domain$goal$GoalStatusDTO = value;
    this.isSet$goalStatusDTO$com$revaluate$domain$goal$GoalStatusDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GoalDTO#createdDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalDTOBuilder withCreatedDate(LocalDateTime value) {
    this.value$createdDate$org$joda$time$LocalDateTime = value;
    this.isSet$createdDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GoalDTO#modifiedDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalDTOBuilder withModifiedDate(LocalDateTime value) {
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
      GoalDTOBuilder result = (GoalDTOBuilder)super.clone();
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
  public GoalDTOBuilder but() {
    return (GoalDTOBuilder)clone();
  }

  /**
   * Creates a new {@link GoalDTO} based on this builder's settings.
   *
   * @return the created GoalDTO
   */
  public GoalDTO build() {
    try {
      GoalDTO result = new GoalDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$value$double) {
        result.setValue(value$value$double);
      }
      if (isSet$goalTarget$com$revaluate$domain$goal$GoalTarget) {
        result.setGoalTarget(value$goalTarget$com$revaluate$domain$goal$GoalTarget);
      }
      if (isSet$category$com$revaluate$domain$category$CategoryDTO) {
        result.setCategory(value$category$com$revaluate$domain$category$CategoryDTO);
      }
      if (isSet$startDate$org$joda$time$LocalDateTime) {
        result.setStartDate(value$startDate$org$joda$time$LocalDateTime);
      }
      if (isSet$endDate$org$joda$time$LocalDateTime) {
        result.setEndDate(value$endDate$org$joda$time$LocalDateTime);
      }
      if (isSet$goalStatusDTO$com$revaluate$domain$goal$GoalStatusDTO) {
        result.setGoalStatusDTO(value$goalStatusDTO$com$revaluate$domain$goal$GoalStatusDTO);
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
