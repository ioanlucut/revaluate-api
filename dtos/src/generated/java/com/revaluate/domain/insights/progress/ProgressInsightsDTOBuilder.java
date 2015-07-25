package com.revaluate.domain.insights.progress;

import com.revaluate.domain.insights.monthly.InsightsMonthlyDTO;
import java.util.List;
import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class ProgressInsightsDTOBuilder
    implements Cloneable {
  protected ProgressInsightsDTOBuilder self;
  protected LocalDateTime value$from$org$joda$time$LocalDateTime;
  protected boolean isSet$from$org$joda$time$LocalDateTime;
  protected LocalDateTime value$to$org$joda$time$LocalDateTime;
  protected boolean isSet$to$org$joda$time$LocalDateTime;
  protected double value$totalAmountSpent$double;
  protected boolean isSet$totalAmountSpent$double;
  protected long value$numberOfTransactions$long;
  protected boolean isSet$numberOfTransactions$long;
  protected List<InsightsMonthlyDTO> value$insightsMonthlyDTO$java$util$List;
  protected boolean isSet$insightsMonthlyDTO$java$util$List;

  /**
   * Creates a new {@link ProgressInsightsDTOBuilder}.
   */
  public ProgressInsightsDTOBuilder() {
    self = (ProgressInsightsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ProgressInsightsDTO#from} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ProgressInsightsDTOBuilder withFrom(LocalDateTime value) {
    this.value$from$org$joda$time$LocalDateTime = value;
    this.isSet$from$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ProgressInsightsDTO#to} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ProgressInsightsDTOBuilder withTo(LocalDateTime value) {
    this.value$to$org$joda$time$LocalDateTime = value;
    this.isSet$to$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ProgressInsightsDTO#totalAmountSpent} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ProgressInsightsDTOBuilder withTotalAmountSpent(double value) {
    this.value$totalAmountSpent$double = value;
    this.isSet$totalAmountSpent$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ProgressInsightsDTO#numberOfTransactions} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ProgressInsightsDTOBuilder withNumberOfTransactions(long value) {
    this.value$numberOfTransactions$long = value;
    this.isSet$numberOfTransactions$long = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ProgressInsightsDTO#insightsMonthlyDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ProgressInsightsDTOBuilder withInsightsMonthlyDTO(List<InsightsMonthlyDTO> value) {
    this.value$insightsMonthlyDTO$java$util$List = value;
    this.isSet$insightsMonthlyDTO$java$util$List = true;
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
      ProgressInsightsDTOBuilder result = (ProgressInsightsDTOBuilder)super.clone();
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
  public ProgressInsightsDTOBuilder but() {
    return (ProgressInsightsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ProgressInsightsDTO} based on this builder's settings.
   *
   * @return the created ProgressInsightsDTO
   */
  public ProgressInsightsDTO build() {
    try {
      ProgressInsightsDTO result = new ProgressInsightsDTO();
      if (isSet$from$org$joda$time$LocalDateTime) {
        result.setFrom(value$from$org$joda$time$LocalDateTime);
      }
      if (isSet$to$org$joda$time$LocalDateTime) {
        result.setTo(value$to$org$joda$time$LocalDateTime);
      }
      if (isSet$totalAmountSpent$double) {
        result.setTotalAmountSpent(value$totalAmountSpent$double);
      }
      if (isSet$numberOfTransactions$long) {
        result.setNumberOfTransactions(value$numberOfTransactions$long);
      }
      if (isSet$insightsMonthlyDTO$java$util$List) {
        result.setInsightsMonthlyDTO(value$insightsMonthlyDTO$java$util$List);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
