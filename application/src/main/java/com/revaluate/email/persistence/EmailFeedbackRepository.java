package com.revaluate.email.persistence;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EmailFeedbackRepository extends AbstractEmailRepository<EmailFeedback, Integer> {

}