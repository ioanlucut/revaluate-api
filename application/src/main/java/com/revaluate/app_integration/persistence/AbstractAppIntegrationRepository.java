package com.revaluate.app_integration.persistence;

import com.revaluate.domain.app_integration.AppIntegrationType;
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

    Optional<T> findOneByAppIntegrationTypeAndSlackUserIdAndSlackTeamId(AppIntegrationType AppIntegrationType, String slackUserId, String slackTeamId);

    //-----------------------------------------------------------------
    // Find all by type and user id
    //-----------------------------------------------------------------
    List<T> findAllByAppIntegrationTypeAndUserId(AppIntegrationType AppIntegrationType, int userId);

    @Modifying
    @Transactional
    @Query("delete from AppIntegration u where u.user.id = ?1")
    void removeByUserId(int userId);

    @Modifying
    @Transactional
    @Query("delete from AppIntegration u where u.AppIntegrationType = ?1 and u.user.id = ?2")
    void deleteAllByAppIntegrationTypeAndUserId(AppIntegrationType AppIntegrationType, int userId);
}