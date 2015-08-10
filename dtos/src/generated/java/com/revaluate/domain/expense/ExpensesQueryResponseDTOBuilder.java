package com.revaluate.domain.expense;

import java.util.List;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ExpensesQueryResponseDTOBuilder
    implements Cloneable {
  protected ExpensesQueryResponseDTOBuilder self;
  protected int value$currentPage$int;
  protected boolean isSet$currentPage$int;
  protected int value$currentSize$int;
  protected boolean isSet$currentSize$int;
  protected int value$totalSize$int;
  protected boolean isSet$totalSize$int;
  protected List<GroupedExpensesDTO> value$groupedExpensesDTOList$java$util$List;
  protected boolean isSet$groupedExpensesDTOList$java$util$List;

  /**
   * Creates a new {@link ExpensesQueryResponseDTOBuilder}.
   */
  public ExpensesQueryResponseDTOBuilder() {
    self = (ExpensesQueryResponseDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ExpensesQueryResponseDTO#currentPage} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpensesQueryResponseDTOBuilder withCurrentPage(int value) {
    this.value$currentPage$int = value;
    this.isSet$currentPage$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpensesQueryResponseDTO#currentSize} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpensesQueryResponseDTOBuilder withCurrentSize(int value) {
    this.value$currentSize$int = value;
    this.isSet$currentSize$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpensesQueryResponseDTO#totalSize} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpensesQueryResponseDTOBuilder withTotalSize(int value) {
    this.value$totalSize$int = value;
    this.isSet$totalSize$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpensesQueryResponseDTO#groupedExpensesDTOList} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpensesQueryResponseDTOBuilder withGroupedExpensesDTOList(List<GroupedExpensesDTO> value) {
    this.value$groupedExpensesDTOList$java$util$List = value;
    this.isSet$groupedExpensesDTOList$java$util$List = true;
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
      ExpensesQueryResponseDTOBuilder result = (ExpensesQueryResponseDTOBuilder)super.clone();
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
  public ExpensesQueryResponseDTOBuilder but() {
    return (ExpensesQueryResponseDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ExpensesQueryResponseDTO} based on this builder's settings.
   *
   * @return the created ExpensesQueryResponseDTO
   */
  public ExpensesQueryResponseDTO build() {
    try {
      ExpensesQueryResponseDTO result = new ExpensesQueryResponseDTO();
      if (isSet$currentPage$int) {
        result.setCurrentPage(value$currentPage$int);
      }
      if (isSet$currentSize$int) {
        result.setCurrentSize(value$currentSize$int);
      }
      if (isSet$totalSize$int) {
        result.setTotalSize(value$totalSize$int);
      }
      if (isSet$groupedExpensesDTOList$java$util$List) {
        result.setGroupedExpensesDTOList(value$groupedExpensesDTOList$java$util$List);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
