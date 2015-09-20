package com.revaluate.app_integration.persistence;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AppIntegrationSlackRepository extends AbstractAppIntegrationRepository<AppIntegrationSlack, Integer> {

}