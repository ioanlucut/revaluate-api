package com.revaluate.account.persistence;

import com.revaluate.domain.email.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
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

    Optional<EmailToken> findOneByEmailTypeAndUserId(EmailType emailType, int userId);


    //-----------------------------------------------------------------
    // Find all by type and user id
    //-----------------------------------------------------------------
    List<EmailToken> findAllByEmailTypeAndUserId(EmailType emailType, int userId);

    //-----------------------------------------------------------------
    // By USER ID validated/non validated
    //-----------------------------------------------------------------
    Optional<EmailToken> findOneByUserIdAndValidatedTrue(int userId);

    Optional<EmailToken> findOneByUserIdAndValidatedFalse(int userId);

    //-----------------------------------------------------------------
    // All validated/non validated
    //-----------------------------------------------------------------
    List<EmailToken> findAllByValidatedTrue();

    List<EmailToken> findAllByValidatedFalse();

    @Modifying
    @Transactional
    @Query("delete from EmailToken u where u.user.id = ?1")
    void removeByUserId(int userId);
}