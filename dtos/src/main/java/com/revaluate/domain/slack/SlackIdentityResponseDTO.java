package com.revaluate.domain.slack;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@GeneratePojoBuilder
public class SlackIdentityResponseDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotEmpty
    private String ok;

    @NotEmpty
    @JsonProperty("team_id")
    private String teamId;

    @NotEmpty
    @JsonProperty("user_id")
    private String userId;

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SlackIdentityResponseDTO{" +
                "ok='" + ok + '\'' +
                ", teamId='" + teamId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}