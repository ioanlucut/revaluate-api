package com.revaluate.domain.slack;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class SlackDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotEmpty
    private String token;

    @NotEmpty
    @JsonProperty("team_id")
    private String teamId;

    @NotEmpty
    @JsonProperty("team_domain")
    private String teamDomain;

    @NotEmpty
    @JsonProperty("channel_id")
    private String channelId;

    @NotEmpty
    @JsonProperty("channel_name")
    private String channelName;

    @NotEmpty
    @JsonProperty("user_id")
    private String userId;

    @NotEmpty
    @JsonProperty("user_name")
    private String userName;

    @NotEmpty
    @JsonProperty("command")
    private String command;

    @NotEmpty
    @JsonProperty("text")
    private String text;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamDomain() {
        return teamDomain;
    }

    public void setTeamDomain(String teamDomain) {
        this.teamDomain = teamDomain;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SlackDTO)) return false;
        SlackDTO slackDTO = (SlackDTO) o;
        return Objects.equals(token, slackDTO.token) &&
                Objects.equals(teamId, slackDTO.teamId) &&
                Objects.equals(teamDomain, slackDTO.teamDomain) &&
                Objects.equals(channelId, slackDTO.channelId) &&
                Objects.equals(channelName, slackDTO.channelName) &&
                Objects.equals(userId, slackDTO.userId) &&
                Objects.equals(userName, slackDTO.userName) &&
                Objects.equals(command, slackDTO.command) &&
                Objects.equals(text, slackDTO.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, teamId, teamDomain, channelId, channelName, userId, userName, command, text);
    }

    @Override
    public String toString() {
        return "SlackDTO{" +
                "token='" + token + '\'' +
                ", teamId='" + teamId + '\'' +
                ", teamDomain='" + teamDomain + '\'' +
                ", channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", command='" + command + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}