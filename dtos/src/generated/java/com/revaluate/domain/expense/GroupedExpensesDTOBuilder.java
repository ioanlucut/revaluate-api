package com.revaluate.domain.expense;

import java.util.List;
import javax.annotation.Generated;
import org.joda.time.LocalDate;

@Generated("PojoBuilder")
public class GroupedExpensesDTOBuilder
    implements Cloneable {
  protected GroupedExpensesDTOBuilder self;
  protected List<ExpenseDTO> value$expenseDTOs$java$util$List;
  protected boolean isSet$expenseDTOs$java$util$List;
  protected LocalDate value$localDate$org$joda$time$LocalDate;
  protected boolean isSet$localDate$org$joda$time$LocalDate;

  /**
   * Creates a new {@link GroupedExpensesDTOBuilder}.
   */
  public GroupedExpensesDTOBuilder() {
    self = (GroupedExpensesDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link GroupedExpensesDTO#expenseDTOs} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GroupedExpensesDTOBuilder withExpenseDTOs(List<ExpenseDTO> value) {
    this.value$expenseDTOs$java$util$List = value;
    this.isSet$expenseDTOs$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GroupedExpensesDTO#localDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GroupedExpensesDTOBuilder withLocalDate(LocalDate value) {
    this.value$localDate$org$joda$time$LocalDate = value;
    this.isSet$localDate$org$joda$time$LocalDate = true;
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
      GroupedExpensesDTOBuilder result = (GroupedExpensesDTOBuilder)super.clone();
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
  public GroupedExpensesDTOBuilder but() {
    return (GroupedExpensesDTOBuilder)clone();
  }

  /**
   * Creates a new {@link GroupedExpensesDTO} based on this builder's settings.
   *
   * @return the created GroupedExpensesDTO
   */
  public GroupedExpensesDTO build() {
    try {
      GroupedExpensesDTO result = new GroupedExpensesDTO();
      if (isSet$expenseDTOs$java$util$List) {
        result.setExpenseDTOs(value$expenseDTOs$java$util$List);
      }
      if (isSet$localDate$org$joda$time$LocalDate) {
        result.setLocalDate(value$localDate$org$joda$time$LocalDate);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
