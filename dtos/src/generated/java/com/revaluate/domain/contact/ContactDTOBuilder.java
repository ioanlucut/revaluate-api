package com.revaluate.domain.contact;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ContactDTOBuilder
    implements Cloneable {
  protected ContactDTOBuilder self;
  protected String value$name$java$lang$String;
  protected boolean isSet$name$java$lang$String;
  protected String value$email$java$lang$String;
  protected boolean isSet$email$java$lang$String;
  protected String value$message$java$lang$String;
  protected boolean isSet$message$java$lang$String;

  /**
   * Creates a new {@link ContactDTOBuilder}.
   */
  public ContactDTOBuilder() {
    self = (ContactDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ContactDTO#name} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ContactDTOBuilder withName(String value) {
    this.value$name$java$lang$String = value;
    this.isSet$name$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ContactDTO#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ContactDTOBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ContactDTO#message} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ContactDTOBuilder withMessage(String value) {
    this.value$message$java$lang$String = value;
    this.isSet$message$java$lang$String = true;
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
      ContactDTOBuilder result = (ContactDTOBuilder)super.clone();
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
  public ContactDTOBuilder but() {
    return (ContactDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ContactDTO} based on this builder's settings.
   *
   * @return the created ContactDTO
   */
  public ContactDTO build() {
    try {
      ContactDTO result = new ContactDTO();
      if (isSet$name$java$lang$String) {
        result.setName(value$name$java$lang$String);
      }
      if (isSet$email$java$lang$String) {
        result.setEmail(value$email$java$lang$String);
      }
      if (isSet$message$java$lang$String) {
        result.setMessage(value$message$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
