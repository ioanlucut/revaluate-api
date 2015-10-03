package com.revaluate.domain.oauth;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class AppSlackIntegrationDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotEmpty
    private String teamId;

    @NotEmpty
    private String teamName;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String accessToken;

    @NotEmpty
    private String scopes;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppSlackIntegrationDTO)) return false;
        AppSlackIntegrationDTO that = (AppSlackIntegrationDTO) o;
        return Objects.equals(teamId, that.teamId) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(scopes, that.scopes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, teamName, userId, accessToken, scopes);
    }

    @Override
    public String toString() {
        return "AppSlackIntegrationDTO{" +
                "teamId='" + teamId + '\'' +
                ", teamName='" + teamName + '\'' +
                ", userId='" + userId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", scopes='" + scopes + '\'' +
                '}';
    }
}