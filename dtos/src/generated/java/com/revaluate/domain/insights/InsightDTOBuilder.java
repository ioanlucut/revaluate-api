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
  protected double value$totalAmountSpent$double;
  protected boolean isSet$totalAmountSpent$double;
  protected long value$numberOfTransactions$long;
  protected boolean isSet$numberOfTransactions$long;
  protected long value$totalNumberOfTransactions$long;
  protected boolean isSet$totalNumberOfTransactions$long;
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
  public InsightDTOBuilder withNumberOfTransactions(long value) {
    this.value$numberOfTransactions$long = value;
    this.isSet$numberOfTransactions$long = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#totalNumberOfTransactions} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withTotalNumberOfTransactions(long value) {
    this.value$totalNumberOfTransactions$long = value;
    this.isSet$totalNumberOfTransactions$long = true;
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
      if (isSet$totalAmountSpent$double) {
        result.setTotalAmountSpent(value$totalAmountSpent$double);
      }
      if (isSet$numberOfTransactions$long) {
        result.setNumberOfTransactions(value$numberOfTransactions$long);
      }
      if (isSet$totalNumberOfTransactions$long) {
        result.setTotalNumberOfTransactions(value$totalNumberOfTransactions$long);
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
