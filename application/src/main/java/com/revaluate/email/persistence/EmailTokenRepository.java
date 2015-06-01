package com.revaluate.email.persistence;

import com.revaluate.domain.email.EmailType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface EmailTokenRepository extends AbstractEmailRepository<EmailToken, Integer> {

    //-----------------------------------------------------------------
    // By USER ID validated/non validated
    //-----------------------------------------------------------------
    Optional<EmailToken> findOneByTokenAndUserIdAndTokenValidatedTrue(String token, int userId);

    Optional<EmailToken> findOneByTokenAndUserIdAndTokenValidatedFalse(String token, int userId);

    //-----------------------------------------------------------------
    // By email type validated/non validated
    //-----------------------------------------------------------------
    Optional<EmailToken> findOneByEmailTypeAndUserIdAndTokenValidatedTrue(EmailType emailType, int userId);

    Optional<EmailToken> findOneByEmailTypeAndUserIdAndTokenValidatedFalse(EmailType emailType, int userId);

    //-----------------------------------------------------------------
    // Check if there's a token validated.
    //-----------------------------------------------------------------
    Optional<EmailToken> findOneByEmailTypeAndUserIdAndTokenAndTokenValidatedTrue(EmailType emailType, int userId, String token);

    Optional<EmailToken> findOneByEmailTypeAndUserIdAndTokenAndTokenValidatedFalse(EmailType emailType, String token, int userId);

    Optional<EmailToken> findOneByEmailTypeAndUserIdAndToken(EmailType emailType, int userId, String token);

    //-----------------------------------------------------------------
    // By USER ID validated/non validated
    //-----------------------------------------------------------------
    Optional<EmailToken> findOneByUserIdAndTokenValidatedTrue(int userId);

    Optional<EmailToken> findOneByUserIdAndTokenValidatedFalse(int userId);

    //-----------------------------------------------------------------
    // All validated/non validated
    //-----------------------------------------------------------------
    List<EmailToken> findAllByTokenValidatedTrue();

    List<EmailToken> findAllByTokenValidatedFalse();
}