package com.revaluate.domain.insights;

import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class SummaryInsightsDTOBuilder
    implements Cloneable {
  protected SummaryInsightsDTOBuilder self;
  protected LocalDateTime value$firstExistingExpenseDate$org$joda$time$LocalDateTime;
  protected boolean isSet$firstExistingExpenseDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$lastExistingExpenseDate$org$joda$time$LocalDateTime;
  protected boolean isSet$lastExistingExpenseDate$org$joda$time$LocalDateTime;

  /**
   * Creates a new {@link SummaryInsightsDTOBuilder}.
   */
  public SummaryInsightsDTOBuilder() {
    self = (SummaryInsightsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link SummaryInsightsDTO#firstExistingExpenseDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SummaryInsightsDTOBuilder withFirstExistingExpenseDate(LocalDateTime value) {
    this.value$firstExistingExpenseDate$org$joda$time$LocalDateTime = value;
    this.isSet$firstExistingExpenseDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SummaryInsightsDTO#lastExistingExpenseDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SummaryInsightsDTOBuilder withLastExistingExpenseDate(LocalDateTime value) {
    this.value$lastExistingExpenseDate$org$joda$time$LocalDateTime = value;
    this.isSet$lastExistingExpenseDate$org$joda$time$LocalDateTime = true;
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
      SummaryInsightsDTOBuilder result = (SummaryInsightsDTOBuilder)super.clone();
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
  public SummaryInsightsDTOBuilder but() {
    return (SummaryInsightsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link SummaryInsightsDTO} based on this builder's settings.
   *
   * @return the created SummaryInsightsDTO
   */
  public SummaryInsightsDTO build() {
    try {
      SummaryInsightsDTO result = new SummaryInsightsDTO();
      if (isSet$firstExistingExpenseDate$org$joda$time$LocalDateTime) {
        result.setFirstExistingExpenseDate(value$firstExistingExpenseDate$org$joda$time$LocalDateTime);
      }
      if (isSet$lastExistingExpenseDate$org$joda$time$LocalDateTime) {
        result.setLastExistingExpenseDate(value$lastExistingExpenseDate$org$joda$time$LocalDateTime);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
