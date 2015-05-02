package com.revaluate.domain.insights;

import java.util.List;
import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class InsightDTOBuilder
    implements Cloneable {
  protected InsightDTOBuilder self;
  protected LocalDateTime value$from$org$joda$time$LocalDateTime;
  protected boolean isSet$from$org$joda$time$LocalDateTime;
  protected LocalDateTime value$to$org$joda$time$LocalDateTime;
  protected boolean isSet$to$org$joda$time$LocalDateTime;
  protected List<String> value$insightData$java$util$List;
  protected boolean isSet$insightData$java$util$List;
  protected List<String> value$insightColors$java$util$List;
  protected boolean isSet$insightColors$java$util$List;
  protected List<String> value$insightLabels$java$util$List;
  protected boolean isSet$insightLabels$java$util$List;
  protected double value$totalAmountSpent$double;
  protected boolean isSet$totalAmountSpent$double;
  protected int value$numberOfTransactions$int;
  protected boolean isSet$numberOfTransactions$int;
  protected List<TotalPerCategoryInsightDTO> value$totalPerCategoryInsightDTOs$java$util$List;
  protected boolean isSet$totalPerCategoryInsightDTOs$java$util$List;

  /**
   * Creates a new {@link InsightDTOBuilder}.
   */
  public InsightDTOBuilder() {
    self = (InsightDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link InsightDTO#from} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withFrom(LocalDateTime value) {
    this.value$from$org$joda$time$LocalDateTime = value;
    this.isSet$from$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#to} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withTo(LocalDateTime value) {
    this.value$to$org$joda$time$LocalDateTime = value;
    this.isSet$to$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#insightData} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withInsightData(List<String> value) {
    this.value$insightData$java$util$List = value;
    this.isSet$insightData$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#insightColors} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withInsightColors(List<String> value) {
    this.value$insightColors$java$util$List = value;
    this.isSet$insightColors$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#insightLabels} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withInsightLabels(List<String> value) {
    this.value$insightLabels$java$util$List = value;
    this.isSet$insightLabels$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#totalAmountSpent} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withTotalAmountSpent(double value) {
    this.value$totalAmountSpent$double = value;
    this.isSet$totalAmountSpent$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#numberOfTransactions} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withNumberOfTransactions(int value) {
    this.value$numberOfTransactions$int = value;
    this.isSet$numberOfTransactions$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#totalPerCategoryInsightDTOs} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withTotalPerCategoryInsightDTOs(List<TotalPerCategoryInsightDTO> value) {
    this.value$totalPerCategoryInsightDTOs$java$util$List = value;
    this.isSet$totalPerCategoryInsightDTOs$java$util$List = true;
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
      InsightDTOBuilder result = (InsightDTOBuilder)super.clone();
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
  public InsightDTOBuilder but() {
    return (InsightDTOBuilder)clone();
  }

  /**
   * Creates a new {@link InsightDTO} based on this builder's settings.
   *
   * @return the created InsightDTO
   */
  public InsightDTO build() {
    try {
      InsightDTO result = new InsightDTO();
      if (isSet$from$org$joda$time$LocalDateTime) {
        result.setFrom(value$from$org$joda$time$LocalDateTime);
      }
      if (isSet$to$org$joda$time$LocalDateTime) {
        result.setTo(value$to$org$joda$time$LocalDateTime);
      }
      if (isSet$insightData$java$util$List) {
        result.setInsightData(value$insightData$java$util$List);
      }
      if (isSet$insightColors$java$util$List) {
        result.setInsightColors(value$insightColors$java$util$List);
      }
      if (isSet$insightLabels$java$util$List) {
        result.setInsightLabels(value$insightLabels$java$util$List);
      }
      if (isSet$totalAmountSpent$double) {
        result.setTotalAmountSpent(value$totalAmountSpent$double);
      }
      if (isSet$numberOfTransactions$int) {
        result.setNumberOfTransactions(value$numberOfTransactions$int);
      }
      if (isSet$totalPerCategoryInsightDTOs$java$util$List) {
        result.setTotalPerCategoryInsightDTOs(value$totalPerCategoryInsightDTOs$java$util$List);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
