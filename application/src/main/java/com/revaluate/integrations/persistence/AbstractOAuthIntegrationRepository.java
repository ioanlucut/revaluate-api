package com.revaluate.integrations.persistence;

import com.revaluate.domain.account.OauthIntegrationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractOauthIntegrationRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    Optional<T> findOneByOauthIntegrationTypeAndSlackUserIdAndSlackTeamId(OauthIntegrationType oauthIntegrationType, String slackUserId, String slackTeamId);

    //-----------------------------------------------------------------
    // Find all by type and user id
    //-----------------------------------------------------------------
    List<T> findAllByOauthIntegrationTypeAndUserId(OauthIntegrationType oauthIntegrationType, int userId);

    @Modifying
    @Transactional
    @Query("delete from OauthIntegration u where u.user.id = ?1")
    void removeByUserId(int userId);

    @Modifying
    @Transactional
    @Query("delete from OauthIntegration u where u.oauthIntegrationType = ?1 and u.user.id = ?2")
    void deleteAllByOauthIntegrationTypeAndUserId(OauthIntegrationType oauthIntegrationType, int userId);
}