package com.revaluate.oauth.persistence;

import com.revaluate.domain.oauth.AppIntegrationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractAppIntegrationRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    Optional<T> findOneByAppIntegrationTypeAndSlackUserIdAndSlackTeamIdAndUserId(AppIntegrationType appIntegrationType, String slackUserId, String slackTeamId, int userId);

    //-----------------------------------------------------------------
    // Find all by type and user id
    //-----------------------------------------------------------------
    List<T> findAllByAppIntegrationTypeAndUserId(AppIntegrationType appIntegrationType, int userId);

    @Modifying
    @Transactional
    @Query("delete from AppIntegration app where app.id = ?1 and app.user.id = ?2")
    void deleteAllByAppIntegrationIdAndUserId(int appIntegrationId, int userId);
}