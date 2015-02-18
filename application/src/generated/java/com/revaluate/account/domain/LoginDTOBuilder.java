package com.revaluate.account.domain;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class LoginDTOBuilder
    implements Cloneable {
  protected LoginDTOBuilder self;
  protected String value$email$java$lang$String;
  protected boolean isSet$email$java$lang$String;
  protected String value$password$java$lang$String;
  protected boolean isSet$password$java$lang$String;

  /**
   * Creates a new {@link LoginDTOBuilder}.
   */
  public LoginDTOBuilder() {
    self = (LoginDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link LoginDTO#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public LoginDTOBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link LoginDTO#password} property.
   *
   * @param value the default value
   * @return this builder
   */
  public LoginDTOBuilder withPassword(String value) {
    this.value$password$java$lang$String = value;
    this.isSet$password$java$lang$String = true;
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
      LoginDTOBuilder result = (LoginDTOBuilder)super.clone();
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
  public LoginDTOBuilder but() {
    return (LoginDTOBuilder)clone();
  }

  /**
   * Creates a new {@link LoginDTO} based on this builder's settings.
   *
   * @return the created LoginDTO
   */
  public LoginDTO build() {
    try {
      LoginDTO result = new LoginDTO();
      if (isSet$email$java$lang$String) {
        result.setEmail(value$email$java$lang$String);
      }
      if (isSet$password$java$lang$String) {
        result.setPassword(value$password$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
