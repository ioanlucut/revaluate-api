package com.revaluate.domain.insights.daily;

import java.util.List;
import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class InsightsDailyDTOBuilder
    implements Cloneable {
  protected InsightsDailyDTOBuilder self;
  protected LocalDateTime value$from$org$joda$time$LocalDateTime;
  protected boolean isSet$from$org$joda$time$LocalDateTime;
  protected LocalDateTime value$to$org$joda$time$LocalDateTime;
  protected boolean isSet$to$org$joda$time$LocalDateTime;
  protected double value$totalAmountSpent$double;
  protected boolean isSet$totalAmountSpent$double;
  protected long value$numberOfTransactions$long;
  protected boolean isSet$numberOfTransactions$long;
  protected List<TotalPerDayDTO> value$totalPerDayDTOs$java$util$List;
  protected boolean isSet$totalPerDayDTOs$java$util$List;

  /**
   * Creates a new {@link InsightsDailyDTOBuilder}.
   */
  public InsightsDailyDTOBuilder() {
    self = (InsightsDailyDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link InsightsDailyDTO#from} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsDailyDTOBuilder withFrom(LocalDateTime value) {
    this.value$from$org$joda$time$LocalDateTime = value;
    this.isSet$from$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsDailyDTO#to} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsDailyDTOBuilder withTo(LocalDateTime value) {
    this.value$to$org$joda$time$LocalDateTime = value;
    this.isSet$to$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsDailyDTO#totalAmountSpent} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsDailyDTOBuilder withTotalAmountSpent(double value) {
    this.value$totalAmountSpent$double = value;
    this.isSet$totalAmountSpent$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsDailyDTO#numberOfTransactions} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsDailyDTOBuilder withNumberOfTransactions(long value) {
    this.value$numberOfTransactions$long = value;
    this.isSet$numberOfTransactions$long = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsDailyDTO#totalPerDayDTOs} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsDailyDTOBuilder withTotalPerDayDTOs(List<TotalPerDayDTO> value) {
    this.value$totalPerDayDTOs$java$util$List = value;
    this.isSet$totalPerDayDTOs$java$util$List = true;
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
      InsightsDailyDTOBuilder result = (InsightsDailyDTOBuilder)super.clone();
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
  public InsightsDailyDTOBuilder but() {
    return (InsightsDailyDTOBuilder)clone();
  }

  /**
   * Creates a new {@link InsightsDailyDTO} based on this builder's settings.
   *
   * @return the created InsightsDailyDTO
   */
  public InsightsDailyDTO build() {
    try {
      InsightsDailyDTO result = new InsightsDailyDTO();
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
      if (isSet$totalPerDayDTOs$java$util$List) {
        result.setTotalPerDayDTOs(value$totalPerDayDTOs$java$util$List);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
