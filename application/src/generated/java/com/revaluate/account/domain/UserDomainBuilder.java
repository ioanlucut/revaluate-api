package com.revaluate.account.domain;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class UserDomainBuilder
    implements Cloneable {
  protected UserDomainBuilder self;
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

  /**
   * Creates a new {@link UserDomainBuilder}.
   */
  public UserDomainBuilder() {
    self = (UserDomainBuilder)this;
  }

  /**
   * Sets the default value for the {@link UserDomain#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDomainBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDomain#firstName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDomainBuilder withFirstName(String value) {
    this.value$firstName$java$lang$String = value;
    this.isSet$firstName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDomain#lastName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDomainBuilder withLastName(String value) {
    this.value$lastName$java$lang$String = value;
    this.isSet$lastName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDomain#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDomainBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link UserDomain#password} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserDomainBuilder withPassword(String value) {
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
      UserDomainBuilder result = (UserDomainBuilder)super.clone();
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
  public UserDomainBuilder but() {
    return (UserDomainBuilder)clone();
  }

  /**
   * Creates a new {@link UserDomain} based on this builder's settings.
   *
   * @return the created UserDomain
   */
  public UserDomain build() {
    try {
      UserDomain result = new UserDomain();
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
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
