package com.revaluate.domain.insights;

import com.revaluate.domain.category.CategoryDTO;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class CategoryWithTheMostTransactionsInsightsDTOBuilder
    implements Cloneable {
  protected CategoryWithTheMostTransactionsInsightsDTOBuilder self;
  protected CategoryDTO value$categoryDTO$com$revaluate$domain$category$CategoryDTO;
  protected boolean isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO;
  protected int value$numberOfTransactions$int;
  protected boolean isSet$numberOfTransactions$int;

  /**
   * Creates a new {@link CategoryWithTheMostTransactionsInsightsDTOBuilder}.
   */
  public CategoryWithTheMostTransactionsInsightsDTOBuilder() {
    self = (CategoryWithTheMostTransactionsInsightsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link CategoryWithTheMostTransactionsInsightsDTO#categoryDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public CategoryWithTheMostTransactionsInsightsDTOBuilder withCategoryDTO(CategoryDTO value) {
    this.value$categoryDTO$com$revaluate$domain$category$CategoryDTO = value;
    this.isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link CategoryWithTheMostTransactionsInsightsDTO#numberOfTransactions} property.
   *
   * @param value the default value
   * @return this builder
   */
  public CategoryWithTheMostTransactionsInsightsDTOBuilder withNumberOfTransactions(int value) {
    this.value$numberOfTransactions$int = value;
    this.isSet$numberOfTransactions$int = true;
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
      CategoryWithTheMostTransactionsInsightsDTOBuilder result = (CategoryWithTheMostTransactionsInsightsDTOBuilder)super.clone();
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
  public CategoryWithTheMostTransactionsInsightsDTOBuilder but() {
    return (CategoryWithTheMostTransactionsInsightsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link CategoryWithTheMostTransactionsInsightsDTO} based on this builder's settings.
   *
   * @return the created CategoryWithTheMostTransactionsInsightsDTO
   */
  public CategoryWithTheMostTransactionsInsightsDTO build() {
    try {
      CategoryWithTheMostTransactionsInsightsDTO result = new CategoryWithTheMostTransactionsInsightsDTO();
      if (isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO) {
        result.setCategoryDTO(value$categoryDTO$com$revaluate$domain$category$CategoryDTO);
      }
      if (isSet$numberOfTransactions$int) {
        result.setNumberOfTransactions(value$numberOfTransactions$int);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
