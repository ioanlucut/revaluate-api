package com.revaluate.domain.oauth;

import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class AppIntegrationDTOBuilder
    implements Cloneable {
  protected AppIntegrationDTOBuilder self;
  protected Integer value$id$java$lang$Integer;
  protected boolean isSet$id$java$lang$Integer;
  protected AppIntegrationType value$appIntegrationType$com$revaluate$domain$oauth$AppIntegrationType;
  protected boolean isSet$appIntegrationType$com$revaluate$domain$oauth$AppIntegrationType;
  protected AppIntegrationScopeType value$appIntegrationScopeType$com$revaluate$domain$oauth$AppIntegrationScopeType;
  protected boolean isSet$appIntegrationScopeType$com$revaluate$domain$oauth$AppIntegrationScopeType;
  protected LocalDateTime value$createdDate$org$joda$time$LocalDateTime;
  protected boolean isSet$createdDate$org$joda$time$LocalDateTime;
  protected LocalDateTime value$modifiedDate$org$joda$time$LocalDateTime;
  protected boolean isSet$modifiedDate$org$joda$time$LocalDateTime;
  protected String value$slackUserId$java$lang$String;
  protected boolean isSet$slackUserId$java$lang$String;
  protected String value$slackTeamId$java$lang$String;
  protected boolean isSet$slackTeamId$java$lang$String;

  /**
   * Creates a new {@link AppIntegrationDTOBuilder}.
   */
  public AppIntegrationDTOBuilder() {
    self = (AppIntegrationDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link AppIntegrationDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppIntegrationDTOBuilder withId(Integer value) {
    this.value$id$java$lang$Integer = value;
    this.isSet$id$java$lang$Integer = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppIntegrationDTO#appIntegrationType} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppIntegrationDTOBuilder withAppIntegrationType(AppIntegrationType value) {
    this.value$appIntegrationType$com$revaluate$domain$oauth$AppIntegrationType = value;
    this.isSet$appIntegrationType$com$revaluate$domain$oauth$AppIntegrationType = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppIntegrationDTO#appIntegrationScopeType} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppIntegrationDTOBuilder withAppIntegrationScopeType(AppIntegrationScopeType value) {
    this.value$appIntegrationScopeType$com$revaluate$domain$oauth$AppIntegrationScopeType = value;
    this.isSet$appIntegrationScopeType$com$revaluate$domain$oauth$AppIntegrationScopeType = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppIntegrationDTO#createdDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppIntegrationDTOBuilder withCreatedDate(LocalDateTime value) {
    this.value$createdDate$org$joda$time$LocalDateTime = value;
    this.isSet$createdDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppIntegrationDTO#modifiedDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppIntegrationDTOBuilder withModifiedDate(LocalDateTime value) {
    this.value$modifiedDate$org$joda$time$LocalDateTime = value;
    this.isSet$modifiedDate$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppIntegrationDTO#slackUserId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppIntegrationDTOBuilder withSlackUserId(String value) {
    this.value$slackUserId$java$lang$String = value;
    this.isSet$slackUserId$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppIntegrationDTO#slackTeamId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppIntegrationDTOBuilder withSlackTeamId(String value) {
    this.value$slackTeamId$java$lang$String = value;
    this.isSet$slackTeamId$java$lang$String = true;
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
      AppIntegrationDTOBuilder result = (AppIntegrationDTOBuilder)super.clone();
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
  public AppIntegrationDTOBuilder but() {
    return (AppIntegrationDTOBuilder)clone();
  }

  /**
   * Creates a new {@link AppIntegrationDTO} based on this builder's settings.
   *
   * @return the created AppIntegrationDTO
   */
  public AppIntegrationDTO build() {
    try {
      AppIntegrationDTO result = new AppIntegrationDTO();
      if (isSet$id$java$lang$Integer) {
        result.setId(value$id$java$lang$Integer);
      }
      if (isSet$appIntegrationType$com$revaluate$domain$oauth$AppIntegrationType) {
        result.setAppIntegrationType(value$appIntegrationType$com$revaluate$domain$oauth$AppIntegrationType);
      }
      if (isSet$appIntegrationScopeType$com$revaluate$domain$oauth$AppIntegrationScopeType) {
        result.setAppIntegrationScopeType(value$appIntegrationScopeType$com$revaluate$domain$oauth$AppIntegrationScopeType);
      }
      if (isSet$createdDate$org$joda$time$LocalDateTime) {
        result.setCreatedDate(value$createdDate$org$joda$time$LocalDateTime);
      }
      if (isSet$modifiedDate$org$joda$time$LocalDateTime) {
        result.setModifiedDate(value$modifiedDate$org$joda$time$LocalDateTime);
      }
      if (isSet$slackUserId$java$lang$String) {
        result.setSlackUserId(value$slackUserId$java$lang$String);
      }
      if (isSet$slackTeamId$java$lang$String) {
        result.setSlackTeamId(value$slackTeamId$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
