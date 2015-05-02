package com.revaluate.domain.insights;

import com.revaluate.domain.category.CategoryDTO;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class TotalPerCategoryInsightDTOBuilder
    implements Cloneable {
  protected TotalPerCategoryInsightDTOBuilder self;
  protected CategoryDTO value$categoryDTO$com$revaluate$domain$category$CategoryDTO;
  protected boolean isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO;
  protected String value$totalAmount$java$lang$String;
  protected boolean isSet$totalAmount$java$lang$String;

  /**
   * Creates a new {@link TotalPerCategoryInsightDTOBuilder}.
   */
  public TotalPerCategoryInsightDTOBuilder() {
    self = (TotalPerCategoryInsightDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link TotalPerCategoryInsightDTO#categoryDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerCategoryInsightDTOBuilder withCategoryDTO(CategoryDTO value) {
    this.value$categoryDTO$com$revaluate$domain$category$CategoryDTO = value;
    this.isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link TotalPerCategoryInsightDTO#totalAmount} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerCategoryInsightDTOBuilder withTotalAmount(String value) {
    this.value$totalAmount$java$lang$String = value;
    this.isSet$totalAmount$java$lang$String = true;
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
      TotalPerCategoryInsightDTOBuilder result = (TotalPerCategoryInsightDTOBuilder)super.clone();
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
  public TotalPerCategoryInsightDTOBuilder but() {
    return (TotalPerCategoryInsightDTOBuilder)clone();
  }

  /**
   * Creates a new {@link TotalPerCategoryInsightDTO} based on this builder's settings.
   *
   * @return the created TotalPerCategoryInsightDTO
   */
  public TotalPerCategoryInsightDTO build() {
    try {
      TotalPerCategoryInsightDTO result = new TotalPerCategoryInsightDTO();
      if (isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO) {
        result.setCategoryDTO(value$categoryDTO$com$revaluate$domain$category$CategoryDTO);
      }
      if (isSet$totalAmount$java$lang$String) {
        result.setTotalAmount(value$totalAmount$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
