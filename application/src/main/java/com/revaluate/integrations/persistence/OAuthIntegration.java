package com.revaluate.integrations.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.domain.oauth.OauthIntegrationScopeType;
import com.revaluate.domain.oauth.OauthIntegrationType;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = OauthIntegration.SEQ_GENERATOR_NAME,
        sequenceName = OauthIntegration.SEQ_NAME,
        initialValue = OauthIntegration.SEQ_INITIAL_VALUE,
        allocationSize = OauthIntegration.ALLOCATION_SIZE)
@Table(name = "oauth_integration")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = OauthIntegration.OAUTH_INTEGRATION_TYPE)
public class OauthIntegration implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    public static final String USER_ID = "user_id";
    protected static final String SEQ_NAME = "oauth_integration_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "oauth_integration_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    protected static final int ALLOCATION_SIZE = 1;
    public static final String OAUTH_INTEGRATION_TYPE = "discriminator_oauth_type";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OauthIntegrationType oauthIntegrationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OauthIntegrationScopeType oauthIntegrationScopeType;

    @ManyToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;

    /**
     * The OAUTH access token (initially can be NULL)
     */
    private String accessToken;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @PrePersist
    void createdAt() {
        this.createdDate = this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.modifiedDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OauthIntegrationType getOauthIntegrationType() {
        return oauthIntegrationType;
    }

    public void setOauthIntegrationType(OauthIntegrationType oauthIntegrationType) {
        this.oauthIntegrationType = oauthIntegrationType;
    }

    public OauthIntegrationScopeType getOauthIntegrationScopeType() {
        return oauthIntegrationScopeType;
    }

    public void setOauthIntegrationScopeType(OauthIntegrationScopeType oauthIntegrationScopeType) {
        this.oauthIntegrationScopeType = oauthIntegrationScopeType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
}