package com.revaluate.account.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Integer> {

    //-----------------------------------------------------------------
    // By USER ID validated/non validated
    //-----------------------------------------------------------------
    Optional<EmailToken> findOneByTokenAndUserIdAndValidatedTrue(String token, int userId);

    Optional<EmailToken> findOneByTokenAndUserIdAndValidatedFalse(String token, int userId);

    //-----------------------------------------------------------------
    // By email type validated/non validated
    //-----------------------------------------------------------------
    Optional<EmailToken> findOneByEmailTypeAndUserIdAndValidatedTrue(EmailType emailType, int userId);

    Optional<EmailToken> findOneByEmailTypeAndUserIdAndValidatedFalse(EmailType emailType, int userId);

    //-----------------------------------------------------------------
    // By USER ID validated/non validated
    //-----------------------------------------------------------------
    List<EmailToken> findOneByUserIdAndValidatedTrue(int userId);

    List<EmailToken> findOneByUserIdAndValidatedFalse(int userId);

    //-----------------------------------------------------------------
    // All validated/non validated
    //-----------------------------------------------------------------
    List<EmailToken> findAllByValidatedTrue();

    List<EmailToken> findAllByValidatedFalse();
}