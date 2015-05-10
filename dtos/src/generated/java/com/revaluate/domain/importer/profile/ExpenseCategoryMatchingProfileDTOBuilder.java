package com.revaluate.domain.importer.profile;

import com.revaluate.domain.category.CategoryDTO;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ExpenseCategoryMatchingProfileDTOBuilder
    implements Cloneable {
  protected ExpenseCategoryMatchingProfileDTOBuilder self;
  protected String value$categoryCandidateName$java$lang$String;
  protected boolean isSet$categoryCandidateName$java$lang$String;
  protected CategoryDTO value$categoryDTO$com$revaluate$domain$category$CategoryDTO;
  protected boolean isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO;

  /**
   * Creates a new {@link ExpenseCategoryMatchingProfileDTOBuilder}.
   */
  public ExpenseCategoryMatchingProfileDTOBuilder() {
    self = (ExpenseCategoryMatchingProfileDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ExpenseCategoryMatchingProfileDTO#categoryCandidateName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseCategoryMatchingProfileDTOBuilder withCategoryCandidateName(String value) {
    this.value$categoryCandidateName$java$lang$String = value;
    this.isSet$categoryCandidateName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseCategoryMatchingProfileDTO#categoryDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseCategoryMatchingProfileDTOBuilder withCategoryDTO(CategoryDTO value) {
    this.value$categoryDTO$com$revaluate$domain$category$CategoryDTO = value;
    this.isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO = true;
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
      ExpenseCategoryMatchingProfileDTOBuilder result = (ExpenseCategoryMatchingProfileDTOBuilder)super.clone();
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
  public ExpenseCategoryMatchingProfileDTOBuilder but() {
    return (ExpenseCategoryMatchingProfileDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ExpenseCategoryMatchingProfileDTO} based on this builder's settings.
   *
   * @return the created ExpenseCategoryMatchingProfileDTO
   */
  public ExpenseCategoryMatchingProfileDTO build() {
    try {
      ExpenseCategoryMatchingProfileDTO result = new ExpenseCategoryMatchingProfileDTO();
      if (isSet$categoryCandidateName$java$lang$String) {
        result.setCategoryCandidateName(value$categoryCandidateName$java$lang$String);
      }
      if (isSet$categoryDTO$com$revaluate$domain$category$CategoryDTO) {
        result.setCategoryDTO(value$categoryDTO$com$revaluate$domain$category$CategoryDTO);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
