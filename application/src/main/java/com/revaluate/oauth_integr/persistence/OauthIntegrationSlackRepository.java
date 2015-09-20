package com.revaluate.oauth_integr.persistence;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OauthIntegrationSlackRepository extends AbstractOauthIntegrationRepository<OauthIntegrationSlack, Integer> {

}