package com.revaluate.email.persistence;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EmailRepository extends AbstractEmailRepository<Email, Integer> {

}