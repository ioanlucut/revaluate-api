package com.revaluate.domain.insights.monthly;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import java.util.List;
import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class InsightsMonthlyDTOBuilder
    implements Cloneable {
  protected InsightsMonthlyDTOBuilder self;
  protected LocalDateTime value$from$org$joda$time$LocalDateTime;
  protected boolean isSet$from$org$joda$time$LocalDateTime;
  protected LocalDateTime value$to$org$joda$time$LocalDateTime;
  protected boolean isSet$to$org$joda$time$LocalDateTime;
  protected double value$totalAmountSpent$double;
  protected boolean isSet$totalAmountSpent$double;
  protected long value$numberOfTransactions$long;
  protected boolean isSet$numberOfTransactions$long;
  protected List<TotalPerCategoryInsightsDTO> value$totalPerCategoryInsightsDTOs$java$util$List;
  protected boolean isSet$totalPerCategoryInsightsDTOs$java$util$List;
  protected CategoryDTO value$highestAmountCategory$com$revaluate$domain$category$CategoryDTO;
  protected boolean isSet$highestAmountCategory$com$revaluate$domain$category$CategoryDTO;
  protected CategoryWithTheMostTransactionsInsightsDTO value$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$monthly$CategoryWithTheMostTransactionsInsightsDTO;
  protected boolean isSet$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$monthly$CategoryWithTheMostTransactionsInsightsDTO;
  protected ExpenseDTO value$biggestExpense$com$revaluate$domain$expense$ExpenseDTO;
  protected boolean isSet$biggestExpense$com$revaluate$domain$expense$ExpenseDTO;
  protected double value$differenceBetweenLastMonth$double;
  protected boolean isSet$differenceBetweenLastMonth$double;
  protected double value$differencePercentageBetweenLastMonth$double;
  protected boolean isSet$differencePercentageBetweenLastMonth$double;

  /**
   * Creates a new {@link InsightsMonthlyDTOBuilder}.
   */
  public InsightsMonthlyDTOBuilder() {
    self = (InsightsMonthlyDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#from} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withFrom(LocalDateTime value) {
    this.value$from$org$joda$time$LocalDateTime = value;
    this.isSet$from$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#to} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withTo(LocalDateTime value) {
    this.value$to$org$joda$time$LocalDateTime = value;
    this.isSet$to$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#totalAmountSpent} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withTotalAmountSpent(double value) {
    this.value$totalAmountSpent$double = value;
    this.isSet$totalAmountSpent$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#numberOfTransactions} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withNumberOfTransactions(long value) {
    this.value$numberOfTransactions$long = value;
    this.isSet$numberOfTransactions$long = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#totalPerCategoryInsightsDTOs} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withTotalPerCategoryInsightsDTOs(List<TotalPerCategoryInsightsDTO> value) {
    this.value$totalPerCategoryInsightsDTOs$java$util$List = value;
    this.isSet$totalPerCategoryInsightsDTOs$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#highestAmountCategory} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withHighestAmountCategory(CategoryDTO value) {
    this.value$highestAmountCategory$com$revaluate$domain$category$CategoryDTO = value;
    this.isSet$highestAmountCategory$com$revaluate$domain$category$CategoryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#categoryWithTheMostTransactionsInsightsDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withCategoryWithTheMostTransactionsInsightsDTO(CategoryWithTheMostTransactionsInsightsDTO value) {
    this.value$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$monthly$CategoryWithTheMostTransactionsInsightsDTO = value;
    this.isSet$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$monthly$CategoryWithTheMostTransactionsInsightsDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#biggestExpense} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withBiggestExpense(ExpenseDTO value) {
    this.value$biggestExpense$com$revaluate$domain$expense$ExpenseDTO = value;
    this.isSet$biggestExpense$com$revaluate$domain$expense$ExpenseDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#differenceBetweenLastMonth} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withDifferenceBetweenLastMonth(double value) {
    this.value$differenceBetweenLastMonth$double = value;
    this.isSet$differenceBetweenLastMonth$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsMonthlyDTO#differencePercentageBetweenLastMonth} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthlyDTOBuilder withDifferencePercentageBetweenLastMonth(double value) {
    this.value$differencePercentageBetweenLastMonth$double = value;
    this.isSet$differencePercentageBetweenLastMonth$double = true;
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
      InsightsMonthlyDTOBuilder result = (InsightsMonthlyDTOBuilder)super.clone();
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
  public InsightsMonthlyDTOBuilder but() {
    return (InsightsMonthlyDTOBuilder)clone();
  }

  /**
   * Creates a new {@link InsightsMonthlyDTO} based on this builder's settings.
   *
   * @return the created InsightsMonthlyDTO
   */
  public InsightsMonthlyDTO build() {
    try {
      InsightsMonthlyDTO result = new InsightsMonthlyDTO();
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
      if (isSet$totalPerCategoryInsightsDTOs$java$util$List) {
        result.setTotalPerCategoryInsightsDTOs(value$totalPerCategoryInsightsDTOs$java$util$List);
      }
      if (isSet$highestAmountCategory$com$revaluate$domain$category$CategoryDTO) {
        result.setHighestAmountCategory(value$highestAmountCategory$com$revaluate$domain$category$CategoryDTO);
      }
      if (isSet$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$monthly$CategoryWithTheMostTransactionsInsightsDTO) {
        result.setCategoryWithTheMostTransactionsInsightsDTO(value$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$monthly$CategoryWithTheMostTransactionsInsightsDTO);
      }
      if (isSet$biggestExpense$com$revaluate$domain$expense$ExpenseDTO) {
        result.setBiggestExpense(value$biggestExpense$com$revaluate$domain$expense$ExpenseDTO);
      }
      if (isSet$differenceBetweenLastMonth$double) {
        result.setDifferenceBetweenLastMonth(value$differenceBetweenLastMonth$double);
      }
      if (isSet$differencePercentageBetweenLastMonth$double) {
        result.setDifferencePercentageBetweenLastMonth(value$differencePercentageBetweenLastMonth$double);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
