package com.revaluate.account.domain;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class UpdatePasswordDTOBuilder
    implements Cloneable {
  protected UpdatePasswordDTOBuilder self;
  protected String value$oldPassword$java$lang$String;
  protected boolean isSet$oldPassword$java$lang$String;
  protected String value$newPassword$java$lang$String;
  protected boolean isSet$newPassword$java$lang$String;
  protected String value$newPasswordConfirmation$java$lang$String;
  protected boolean isSet$newPasswordConfirmation$java$lang$String;

  /**
   * Creates a new {@link UpdatePasswordDTOBuilder}.
   */
  public UpdatePasswordDTOBuilder() {
    self = (UpdatePasswordDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link UpdatePasswordDTO#oldPassword} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UpdatePasswordDTOBuilder withOldPassword(String value) {
    this.value$oldPassword$java$lang$String = value;
    this.isSet$oldPassword$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UpdatePasswordDTO#newPassword} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UpdatePasswordDTOBuilder withNewPassword(String value) {
    this.value$newPassword$java$lang$String = value;
    this.isSet$newPassword$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UpdatePasswordDTO#newPasswordConfirmation} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UpdatePasswordDTOBuilder withNewPasswordConfirmation(String value) {
    this.value$newPasswordConfirmation$java$lang$String = value;
    this.isSet$newPasswordConfirmation$java$lang$String = true;
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
      UpdatePasswordDTOBuilder result = (UpdatePasswordDTOBuilder)super.clone();
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
  public UpdatePasswordDTOBuilder but() {
    return (UpdatePasswordDTOBuilder)clone();
  }

  /**
   * Creates a new {@link UpdatePasswordDTO} based on this builder's settings.
   *
   * @return the created UpdatePasswordDTO
   */
  public UpdatePasswordDTO build() {
    try {
      UpdatePasswordDTO result = new UpdatePasswordDTO();
      if (isSet$oldPassword$java$lang$String) {
        result.setOldPassword(value$oldPassword$java$lang$String);
      }
      if (isSet$newPassword$java$lang$String) {
        result.setNewPassword(value$newPassword$java$lang$String);
      }
      if (isSet$newPasswordConfirmation$java$lang$String) {
        result.setNewPasswordConfirmation(value$newPasswordConfirmation$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
