package com.revaluate.app_integration.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.domain.app_integration.AppIntegrationScopeType;
import com.revaluate.domain.app_integration.AppIntegrationType;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = AppIntegration.SEQ_GENERATOR_NAME,
        sequenceName = AppIntegration.SEQ_NAME,
        initialValue = AppIntegration.SEQ_INITIAL_VALUE,
        allocationSize = AppIntegration.ALLOCATION_SIZE)
@Table(name = "app_integration")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = AppIntegration.APP_INTEGRATION_TYPE)
public class AppIntegration implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    public static final String USER_ID = "user_id";
    protected static final String SEQ_NAME = "app_integration_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "app_integration_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    protected static final int ALLOCATION_SIZE = 1;
    public static final String APP_INTEGRATION_TYPE = "discriminator_app_type";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppIntegrationType AppIntegrationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppIntegrationScopeType appIntegrationScopeType;

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

    public AppIntegrationType getAppIntegrationType() {
        return AppIntegrationType;
    }

    public void setAppIntegrationType(AppIntegrationType AppIntegrationType) {
        this.AppIntegrationType = AppIntegrationType;
    }

    public AppIntegrationScopeType getAppIntegrationScopeType() {
        return appIntegrationScopeType;
    }

    public void setAppIntegrationScopeType(AppIntegrationScopeType appIntegrationScopeType) {
        this.appIntegrationScopeType = appIntegrationScopeType;
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