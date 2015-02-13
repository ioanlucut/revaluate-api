package com.revaluate.account.domain;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class UpdatePasswordDomainBuilder
    implements Cloneable {
  protected UpdatePasswordDomainBuilder self;
  protected String value$oldPassword$java$lang$String;
  protected boolean isSet$oldPassword$java$lang$String;
  protected String value$newPassword$java$lang$String;
  protected boolean isSet$newPassword$java$lang$String;
  protected String value$newPasswordConfirmation$java$lang$String;
  protected boolean isSet$newPasswordConfirmation$java$lang$String;

  /**
   * Creates a new {@link UpdatePasswordDomainBuilder}.
   */
  public UpdatePasswordDomainBuilder() {
    self = (UpdatePasswordDomainBuilder)this;
  }

  /**
   * Sets the default value for the {@link UpdatePasswordDomain#oldPassword} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UpdatePasswordDomainBuilder withOldPassword(String value) {
    this.value$oldPassword$java$lang$String = value;
    this.isSet$oldPassword$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UpdatePasswordDomain#newPassword} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UpdatePasswordDomainBuilder withNewPassword(String value) {
    this.value$newPassword$java$lang$String = value;
    this.isSet$newPassword$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UpdatePasswordDomain#newPasswordConfirmation} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UpdatePasswordDomainBuilder withNewPasswordConfirmation(String value) {
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
      UpdatePasswordDomainBuilder result = (UpdatePasswordDomainBuilder)super.clone();
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
  public UpdatePasswordDomainBuilder but() {
    return (UpdatePasswordDomainBuilder)clone();
  }

  /**
   * Creates a new {@link UpdatePasswordDomain} based on this builder's settings.
   *
   * @return the created UpdatePasswordDomain
   */
  public UpdatePasswordDomain build() {
    try {
      UpdatePasswordDomain result = new UpdatePasswordDomain();
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
