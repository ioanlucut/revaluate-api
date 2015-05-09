package com.revaluate.domain.importer.profile;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ExpenseProfileDTOBuilder
    implements Cloneable {
  protected ExpenseProfileDTOBuilder self;
  protected ExpenseProfileEntryDTO value$descriptionExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
  protected boolean isSet$descriptionExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
  protected ExpenseProfileEntryDTO value$categoryExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
  protected boolean isSet$categoryExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
  protected ExpenseProfileEntryDTO value$dateExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
  protected boolean isSet$dateExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
  protected ExpenseProfileEntryDTO value$amountExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
  protected boolean isSet$amountExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
  protected char value$delimiter$char;
  protected boolean isSet$delimiter$char;
  protected String value$spentDateFormat$java$lang$String;
  protected boolean isSet$spentDateFormat$java$lang$String;

  /**
   * Creates a new {@link ExpenseProfileDTOBuilder}.
   */
  public ExpenseProfileDTOBuilder() {
    self = (ExpenseProfileDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileDTO#descriptionExpenseProfileEntryDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileDTOBuilder withDescriptionExpenseProfileEntryDTO(ExpenseProfileEntryDTO value) {
    this.value$descriptionExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO = value;
    this.isSet$descriptionExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileDTO#categoryExpenseProfileEntryDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileDTOBuilder withCategoryExpenseProfileEntryDTO(ExpenseProfileEntryDTO value) {
    this.value$categoryExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO = value;
    this.isSet$categoryExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileDTO#dateExpenseProfileEntryDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileDTOBuilder withDateExpenseProfileEntryDTO(ExpenseProfileEntryDTO value) {
    this.value$dateExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO = value;
    this.isSet$dateExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileDTO#amountExpenseProfileEntryDTO} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileDTOBuilder withAmountExpenseProfileEntryDTO(ExpenseProfileEntryDTO value) {
    this.value$amountExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO = value;
    this.isSet$amountExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileDTO#delimiter} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileDTOBuilder withDelimiter(char value) {
    this.value$delimiter$char = value;
    this.isSet$delimiter$char = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileDTO#spentDateFormat} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileDTOBuilder withSpentDateFormat(String value) {
    this.value$spentDateFormat$java$lang$String = value;
    this.isSet$spentDateFormat$java$lang$String = true;
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
      ExpenseProfileDTOBuilder result = (ExpenseProfileDTOBuilder)super.clone();
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
  public ExpenseProfileDTOBuilder but() {
    return (ExpenseProfileDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ExpenseProfileDTO} based on this builder's settings.
   *
   * @return the created ExpenseProfileDTO
   */
  public ExpenseProfileDTO build() {
    try {
      ExpenseProfileDTO result = new ExpenseProfileDTO();
      if (isSet$descriptionExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO) {
        result.descriptionExpenseProfileEntryDTO = value$descriptionExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
      }
      if (isSet$categoryExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO) {
        result.categoryExpenseProfileEntryDTO = value$categoryExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
      }
      if (isSet$dateExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO) {
        result.dateExpenseProfileEntryDTO = value$dateExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
      }
      if (isSet$amountExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO) {
        result.amountExpenseProfileEntryDTO = value$amountExpenseProfileEntryDTO$com$revaluate$domain$importer$profile$ExpenseProfileEntryDTO;
      }
      if (isSet$delimiter$char) {
        result.delimiter = value$delimiter$char;
      }
      if (isSet$spentDateFormat$java$lang$String) {
        result.spentDateFormat = value$spentDateFormat$java$lang$String;
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
