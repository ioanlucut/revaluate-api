package com.revaluate.domain.oauth;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class AppSlackIntegrationDTOBuilder
    implements Cloneable {
  protected AppSlackIntegrationDTOBuilder self;
  protected String value$teamId$java$lang$String;
  protected boolean isSet$teamId$java$lang$String;
  protected String value$teamName$java$lang$String;
  protected boolean isSet$teamName$java$lang$String;
  protected String value$userId$java$lang$String;
  protected boolean isSet$userId$java$lang$String;
  protected String value$accessToken$java$lang$String;
  protected boolean isSet$accessToken$java$lang$String;
  protected String value$scopes$java$lang$String;
  protected boolean isSet$scopes$java$lang$String;

  /**
   * Creates a new {@link AppSlackIntegrationDTOBuilder}.
   */
  public AppSlackIntegrationDTOBuilder() {
    self = (AppSlackIntegrationDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link AppSlackIntegrationDTO#teamId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppSlackIntegrationDTOBuilder withTeamId(String value) {
    this.value$teamId$java$lang$String = value;
    this.isSet$teamId$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppSlackIntegrationDTO#teamName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppSlackIntegrationDTOBuilder withTeamName(String value) {
    this.value$teamName$java$lang$String = value;
    this.isSet$teamName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppSlackIntegrationDTO#userId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppSlackIntegrationDTOBuilder withUserId(String value) {
    this.value$userId$java$lang$String = value;
    this.isSet$userId$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppSlackIntegrationDTO#accessToken} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppSlackIntegrationDTOBuilder withAccessToken(String value) {
    this.value$accessToken$java$lang$String = value;
    this.isSet$accessToken$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppSlackIntegrationDTO#scopes} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppSlackIntegrationDTOBuilder withScopes(String value) {
    this.value$scopes$java$lang$String = value;
    this.isSet$scopes$java$lang$String = true;
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
      AppSlackIntegrationDTOBuilder result = (AppSlackIntegrationDTOBuilder)super.clone();
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
  public AppSlackIntegrationDTOBuilder but() {
    return (AppSlackIntegrationDTOBuilder)clone();
  }

  /**
   * Creates a new {@link AppSlackIntegrationDTO} based on this builder's settings.
   *
   * @return the created AppSlackIntegrationDTO
   */
  public AppSlackIntegrationDTO build() {
    try {
      AppSlackIntegrationDTO result = new AppSlackIntegrationDTO();
      if (isSet$teamId$java$lang$String) {
        result.setTeamId(value$teamId$java$lang$String);
      }
      if (isSet$teamName$java$lang$String) {
        result.setTeamName(value$teamName$java$lang$String);
      }
      if (isSet$userId$java$lang$String) {
        result.setUserId(value$userId$java$lang$String);
      }
      if (isSet$accessToken$java$lang$String) {
        result.setAccessToken(value$accessToken$java$lang$String);
      }
      if (isSet$scopes$java$lang$String) {
        result.setScopes(value$scopes$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
