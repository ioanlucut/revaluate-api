package com.revaluate.oauth.persistence;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AppIntegrationSlackRepository extends AbstractAppIntegrationRepository<AppIntegrationSlack, Integer> {

}