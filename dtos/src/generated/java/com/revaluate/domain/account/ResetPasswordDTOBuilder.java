package com.revaluate.domain.account;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ResetPasswordDTOBuilder
    implements Cloneable {
  protected ResetPasswordDTOBuilder self;
  protected String value$password$java$lang$String;
  protected boolean isSet$password$java$lang$String;
  protected String value$passwordConfirmation$java$lang$String;
  protected boolean isSet$passwordConfirmation$java$lang$String;

  /**
   * Creates a new {@link ResetPasswordDTOBuilder}.
   */
  public ResetPasswordDTOBuilder() {
    self = (ResetPasswordDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ResetPasswordDTO#password} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ResetPasswordDTOBuilder withPassword(String value) {
    this.value$password$java$lang$String = value;
    this.isSet$password$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ResetPasswordDTO#passwordConfirmation} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ResetPasswordDTOBuilder withPasswordConfirmation(String value) {
    this.value$passwordConfirmation$java$lang$String = value;
    this.isSet$passwordConfirmation$java$lang$String = true;
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
      ResetPasswordDTOBuilder result = (ResetPasswordDTOBuilder)super.clone();
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
  public ResetPasswordDTOBuilder but() {
    return (ResetPasswordDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ResetPasswordDTO} based on this builder's settings.
   *
   * @return the created ResetPasswordDTO
   */
  public ResetPasswordDTO build() {
    try {
      ResetPasswordDTO result = new ResetPasswordDTO();
      if (isSet$password$java$lang$String) {
        result.setPassword(value$password$java$lang$String);
      }
      if (isSet$passwordConfirmation$java$lang$String) {
        result.setPasswordConfirmation(value$passwordConfirmation$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
