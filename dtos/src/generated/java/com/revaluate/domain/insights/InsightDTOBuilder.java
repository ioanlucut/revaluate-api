package com.revaluate.domain.insights;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
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
  protected List<TotalPerCategoryInsightDTO> value$totalPerCategoryInsightDTOs$java$util$List;
  protected boolean isSet$totalPerCategoryInsightDTOs$java$util$List;
  protected CategoryDTO value$highestAmountCategory$com$revaluate$domain$category$CategoryDTO;
  protected boolean isSet$highestAmountCategory$com$revaluate$domain$category$CategoryDTO;
  protected CategoryWithTheMostTransactionsInsightsDTO value$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$CategoryWithTheMostTransactionsInsightsDTO;
  protected boolean isSet$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$CategoryWithTheMostTransactionsInsightsDTO;
  protected ExpenseDTO value$biggestExpense$com$revaluate$domain$expense$ExpenseDTO;
  protected boolean isSet$biggestExpense$com$revaluate$domain$expense$ExpenseDTO;
  protected double value$totalAmountSpent$double;
  protected boolean isSet$totalAmountSpent$double;
  protected long value$numberOfTransactions$long;
  protected boolean isSet$numberOfTransactions$long;
  protected long value$totalNumberOfTransactions$long;
  protected boolean isSet$totalNumberOfTransactions$long;
  protected double value$differenceBetweenLastMonth$double;
  protected boolean isSet$differenceBetweenLastMonth$double;
  protected double value$differencePercentageBetweenLastMonth$double;
  protected boolean isSet$differencePercentageBetweenLastMonth$double;

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
   * Sets the default value for the {@link InsightDTO#highestAmountCategory} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withHighestAmountCategory(CategoryDTO value) {
    this.value$highestAmountCategory$com$revaluate$domain$category$CategoryDTO = value;
    this.isSet$highestAmountCategory$com$revaluate$domain$category$CategoryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#categoryWithTheMostTransactionsInsightsDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withCategoryWithTheMostTransactionsInsightsDTO(CategoryWithTheMostTransactionsInsightsDTO value) {
    this.value$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$CategoryWithTheMostTransactionsInsightsDTO = value;
    this.isSet$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$CategoryWithTheMostTransactionsInsightsDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#biggestExpense} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withBiggestExpense(ExpenseDTO value) {
    this.value$biggestExpense$com$revaluate$domain$expense$ExpenseDTO = value;
    this.isSet$biggestExpense$com$revaluate$domain$expense$ExpenseDTO = true;
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
   * Sets the default value for the {@link InsightDTO#differenceBetweenLastMonth} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withDifferenceBetweenLastMonth(double value) {
    this.value$differenceBetweenLastMonth$double = value;
    this.isSet$differenceBetweenLastMonth$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightDTO#differencePercentageBetweenLastMonth} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightDTOBuilder withDifferencePercentageBetweenLastMonth(double value) {
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
      if (isSet$totalPerCategoryInsightDTOs$java$util$List) {
        result.setTotalPerCategoryInsightDTOs(value$totalPerCategoryInsightDTOs$java$util$List);
      }
      if (isSet$highestAmountCategory$com$revaluate$domain$category$CategoryDTO) {
        result.setHighestAmountCategory(value$highestAmountCategory$com$revaluate$domain$category$CategoryDTO);
      }
      if (isSet$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$CategoryWithTheMostTransactionsInsightsDTO) {
        result.setCategoryWithTheMostTransactionsInsightsDTO(value$categoryWithTheMostTransactionsInsightsDTO$com$revaluate$domain$insights$CategoryWithTheMostTransactionsInsightsDTO);
      }
      if (isSet$biggestExpense$com$revaluate$domain$expense$ExpenseDTO) {
        result.setBiggestExpense(value$biggestExpense$com$revaluate$domain$expense$ExpenseDTO);
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
