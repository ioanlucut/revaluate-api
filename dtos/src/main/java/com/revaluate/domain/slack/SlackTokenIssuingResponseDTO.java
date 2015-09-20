package com.revaluate.domain.slack;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@GeneratePojoBuilder
public class SlackTokenIssuingResponseDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotEmpty
    @JsonProperty("access_token")
    private String accessToken;

    @NotEmpty
    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}