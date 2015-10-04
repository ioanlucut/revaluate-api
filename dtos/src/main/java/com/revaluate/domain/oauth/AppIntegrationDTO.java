package com.revaluate.domain.oauth;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.joda.time.LocalDateTime;

import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class AppIntegrationDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    private Integer id;
    private AppIntegrationType appIntegrationType;
    private AppIntegrationScopeType appIntegrationScopeType;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String slackUserId;
    private String slackTeamId;
    private String slackTeamName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppIntegrationType getAppIntegrationType() {
        return appIntegrationType;
    }

    public void setAppIntegrationType(AppIntegrationType appIntegrationType) {
        this.appIntegrationType = appIntegrationType;
    }

    public AppIntegrationScopeType getAppIntegrationScopeType() {
        return appIntegrationScopeType;
    }

    public void setAppIntegrationScopeType(AppIntegrationScopeType appIntegrationScopeType) {
        this.appIntegrationScopeType = appIntegrationScopeType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getSlackUserId() {
        return slackUserId;
    }

    public void setSlackUserId(String slackUserId) {
        this.slackUserId = slackUserId;
    }

    public String getSlackTeamId() {
        return slackTeamId;
    }

    public void setSlackTeamId(String slackTeamId) {
        this.slackTeamId = slackTeamId;
    }

    public String getSlackTeamName() {
        return slackTeamName;
    }

    public void setSlackTeamName(String slackTeamName) {
        this.slackTeamName = slackTeamName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppIntegrationDTO)) return false;
        AppIntegrationDTO that = (AppIntegrationDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(appIntegrationType, that.appIntegrationType) &&
                Objects.equals(appIntegrationScopeType, that.appIntegrationScopeType) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(modifiedDate, that.modifiedDate) &&
                Objects.equals(slackUserId, that.slackUserId) &&
                Objects.equals(slackTeamId, that.slackTeamId) &&
                Objects.equals(slackTeamName, that.slackTeamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appIntegrationType, appIntegrationScopeType, createdDate, modifiedDate, slackUserId, slackTeamId, slackTeamName);
    }

    @Override
    public String toString() {
        return "AppIntegrationDTO{" +
                "id=" + id +
                ", appIntegrationType=" + appIntegrationType +
                ", appIntegrationScopeType=" + appIntegrationScopeType +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", slackUserId='" + slackUserId + '\'' +
                ", slackTeamId='" + slackTeamId + '\'' +
                ", slackTeamName='" + slackTeamName + '\'' +
                '}';
    }
}