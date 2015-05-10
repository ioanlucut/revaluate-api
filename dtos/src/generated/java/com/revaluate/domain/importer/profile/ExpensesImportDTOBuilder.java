package com.revaluate.domain.importer.profile;

import com.revaluate.domain.expense.ExpenseDTO;
import java.util.List;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ExpensesImportDTOBuilder
    implements Cloneable {
  protected ExpensesImportDTOBuilder self;
  protected List<ExpenseDTO> value$expenseDTOs$java$util$List;
  protected boolean isSet$expenseDTOs$java$util$List;
  protected List<ExpenseCategoryMatchingProfileDTO> value$expenseCategoryMatchingProfileDTOs$java$util$List;
  protected boolean isSet$expenseCategoryMatchingProfileDTOs$java$util$List;

  /**
   * Creates a new {@link ExpensesImportDTOBuilder}.
   */
  public ExpensesImportDTOBuilder() {
    self = (ExpensesImportDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ExpensesImportDTO#expenseDTOs} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpensesImportDTOBuilder withExpenseDTOs(List<ExpenseDTO> value) {
    this.value$expenseDTOs$java$util$List = value;
    this.isSet$expenseDTOs$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpensesImportDTO#expenseCategoryMatchingProfileDTOs} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpensesImportDTOBuilder withExpenseCategoryMatchingProfileDTOs(List<ExpenseCategoryMatchingProfileDTO> value) {
    this.value$expenseCategoryMatchingProfileDTOs$java$util$List = value;
    this.isSet$expenseCategoryMatchingProfileDTOs$java$util$List = true;
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
      ExpensesImportDTOBuilder result = (ExpensesImportDTOBuilder)super.clone();
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
  public ExpensesImportDTOBuilder but() {
    return (ExpensesImportDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ExpensesImportDTO} based on this builder's settings.
   *
   * @return the created ExpensesImportDTO
   */
  public ExpensesImportDTO build() {
    try {
      ExpensesImportDTO result = new ExpensesImportDTO();
      if (isSet$expenseDTOs$java$util$List) {
        result.setExpenseDTOs(value$expenseDTOs$java$util$List);
      }
      if (isSet$expenseCategoryMatchingProfileDTOs$java$util$List) {
        result.setExpenseCategoryMatchingProfileDTOs(value$expenseCategoryMatchingProfileDTOs$java$util$List);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
