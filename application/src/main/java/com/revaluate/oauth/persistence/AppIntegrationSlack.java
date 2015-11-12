package com.revaluate.oauth.persistence;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = {
        @Index(name = AppIntegration.IX_APP_INTEGRATION_MULTI_COLUMN_INDEX, columnList = "appIntegrationType,slackUserId,slackTeamId,user_id")
})
@DiscriminatorValue(value = AppIntegrationSlack.OAUTH_SLACK)
public class AppIntegrationSlack extends AppIntegration {

    public static final String OAUTH_SLACK = "OAUTH_SLACK";

    @NotEmpty
    private String slackUserId;

    @NotEmpty
    private String slackTeamId;

    @NotEmpty
    private String slackTeamName;

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
    public String toString() {
        return "AppIntegrationSlack{" +
                "slackUserId='" + slackUserId + '\'' +
                ", slackTeamId='" + slackTeamId + '\'' +
                ", slackTeamName='" + slackTeamName + '\'' +
                "} " + super.toString();
    }
}