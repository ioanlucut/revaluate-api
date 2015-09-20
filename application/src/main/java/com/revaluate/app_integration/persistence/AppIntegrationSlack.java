package com.revaluate.app_integration.persistence;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = AppIntegrationSlack.OAUTH_SLACK)
public class AppIntegrationSlack extends AppIntegration {

    public static final String OAUTH_SLACK = "OAUTH_SLACK";

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String slackUserId;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String slackTeamId;

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

    @Override
    public String toString() {
        return "OAuthIntegrationSlack{" +
                "slackUserId='" + slackUserId + '\'' +
                ", slackTeamId='" + slackTeamId + '\'' +
                "} " + super.toString();
    }
}