package com.revaluate.domain.insights.monthly;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class TotalPerCategoryInsightsDTOBuilder
    implements Cloneable {
  protected TotalPerCategoryInsightsDTOBuilder self;
  protected CategoryDTO value$categoryDTO$com$revaluate$domain$category$CategoryDTO;
  protected boolean isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO;
  protected ExpenseDTO value$biggestExpense$com$revaluate$domain$expense$ExpenseDTO;
  protected boolean isSet$biggestExpense$com$revaluate$domain$expense$ExpenseDTO;
  protected int value$numberOfTransactions$int;
  protected boolean isSet$numberOfTransactions$int;
  protected String value$totalAmountFormatted$java$lang$String;
  protected boolean isSet$totalAmountFormatted$java$lang$String;
  protected double value$totalAmount$double;
  protected boolean isSet$totalAmount$double;

  /**
   * Creates a new {@link TotalPerCategoryInsightsDTOBuilder}.
   */
  public TotalPerCategoryInsightsDTOBuilder() {
    self = (TotalPerCategoryInsightsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link TotalPerCategoryInsightsDTO#categoryDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerCategoryInsightsDTOBuilder withCategoryDTO(CategoryDTO value) {
    this.value$categoryDTO$com$revaluate$domain$category$CategoryDTO = value;
    this.isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link TotalPerCategoryInsightsDTO#biggestExpense} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerCategoryInsightsDTOBuilder withBiggestExpense(ExpenseDTO value) {
    this.value$biggestExpense$com$revaluate$domain$expense$ExpenseDTO = value;
    this.isSet$biggestExpense$com$revaluate$domain$expense$ExpenseDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link TotalPerCategoryInsightsDTO#numberOfTransactions} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerCategoryInsightsDTOBuilder withNumberOfTransactions(int value) {
    this.value$numberOfTransactions$int = value;
    this.isSet$numberOfTransactions$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link TotalPerCategoryInsightsDTO#totalAmountFormatted} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerCategoryInsightsDTOBuilder withTotalAmountFormatted(String value) {
    this.value$totalAmountFormatted$java$lang$String = value;
    this.isSet$totalAmountFormatted$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link TotalPerCategoryInsightsDTO#totalAmount} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerCategoryInsightsDTOBuilder withTotalAmount(double value) {
    this.value$totalAmount$double = value;
    this.isSet$totalAmount$double = true;
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
      TotalPerCategoryInsightsDTOBuilder result = (TotalPerCategoryInsightsDTOBuilder)super.clone();
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
  public TotalPerCategoryInsightsDTOBuilder but() {
    return (TotalPerCategoryInsightsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link TotalPerCategoryInsightsDTO} based on this builder's settings.
   *
   * @return the created TotalPerCategoryInsightsDTO
   */
  public TotalPerCategoryInsightsDTO build() {
    try {
      TotalPerCategoryInsightsDTO result = new TotalPerCategoryInsightsDTO();
      if (isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO) {
        result.setCategoryDTO(value$categoryDTO$com$revaluate$domain$category$CategoryDTO);
      }
      if (isSet$biggestExpense$com$revaluate$domain$expense$ExpenseDTO) {
        result.setBiggestExpense(value$biggestExpense$com$revaluate$domain$expense$ExpenseDTO);
      }
      if (isSet$numberOfTransactions$int) {
        result.setNumberOfTransactions(value$numberOfTransactions$int);
      }
      if (isSet$totalAmountFormatted$java$lang$String) {
        result.setTotalAmountFormatted(value$totalAmountFormatted$java$lang$String);
      }
      if (isSet$totalAmount$double) {
        result.setTotalAmount(value$totalAmount$double);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
