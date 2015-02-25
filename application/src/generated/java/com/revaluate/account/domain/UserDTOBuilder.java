package com.revaluate.account.domain;

import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.expense.domain.ExpenseDTO;
import java.util.List;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class UserDTOBuilder
    implements Cloneable {
  protected UserDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected String value$firstName$java$lang$String;
  protected boolean isSet$firstName$java$lang$String;
  protected String value$lastName$java$lang$String;
  protected boolean isSet$lastName$java$lang$String;
  protected String value$email$java$lang$String;
  protected boolean isSet$email$java$lang$String;
  protected String value$password$java$lang$String;
  protected boolean isSet$password$java$lang$String;
  protected boolean value$initiated$boolean;
  protected boolean isSet$initiated$boolean;
  protected List<CategoryDTO> value$categories$java$util$List;
  protected boolean isSet$categories$java$util$List;
  protected List<ExpenseDTO> value$expenses$java$util$List;
  protected boolean isSet$expenses$java$util$List;

  /**
   * Creates a new {@link UserDTOBuilder}.
   */
  public UserDTOBuilder() {
    self = (UserDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link UserDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#firstName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withFirstName(String value) {
    this.value$firstName$java$lang$String = value;
    this.isSet$firstName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#lastName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withLastName(String value) {
    this.value$lastName$java$lang$String = value;
    this.isSet$lastName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#password} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withPassword(String value) {
    this.value$password$java$lang$String = value;
    this.isSet$password$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#initiated} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withInitiated(boolean value) {
    this.value$initiated$boolean = value;
    this.isSet$initiated$boolean = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#categories} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withCategories(List<CategoryDTO> value) {
    this.value$categories$java$util$List = value;
    this.isSet$categories$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDTO#expenses} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDTOBuilder withExpenses(List<ExpenseDTO> value) {
    this.value$expenses$java$util$List = value;
    this.isSet$expenses$java$util$List = true;
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
      UserDTOBuilder result = (UserDTOBuilder)super.clone();
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
  public UserDTOBuilder but() {
    return (UserDTOBuilder)clone();
  }

  /**
   * Creates a new {@link UserDTO} based on this builder's settings.
   *
   * @return the created UserDTO
   */
  public UserDTO build() {
    try {
      UserDTO result = new UserDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$firstName$java$lang$String) {
        result.setFirstName(value$firstName$java$lang$String);
      }
      if (isSet$lastName$java$lang$String) {
        result.setLastName(value$lastName$java$lang$String);
      }
      if (isSet$email$java$lang$String) {
        result.setEmail(value$email$java$lang$String);
      }
      if (isSet$password$java$lang$String) {
        result.setPassword(value$password$java$lang$String);
      }
      if (isSet$initiated$boolean) {
        result.setInitiated(value$initiated$boolean);
      }
      if (isSet$categories$java$util$List) {
        result.setCategories(value$categories$java$util$List);
      }
      if (isSet$expenses$java$util$List) {
        result.setExpenses(value$expenses$java$util$List);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
