package com.revaluate.account.persistence;

import com.revaluate.domain.email.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface EmailRepository extends JpaRepository<Email, Integer> {

    //-----------------------------------------------------------------
    // By USER ID validated/non validated
    //-----------------------------------------------------------------
    Optional<Email> findOneByTokenAndUserIdAndTokenValidatedTrue(String token, int userId);

    Optional<Email> findOneByTokenAndUserIdAndTokenValidatedFalse(String token, int userId);

    //-----------------------------------------------------------------
    // By email type validated/non validated
    //-----------------------------------------------------------------
    Optional<Email> findOneByEmailTypeAndUserIdAndTokenValidatedTrue(EmailType emailType, int userId);

    Optional<Email> findOneByEmailTypeAndUserIdAndTokenValidatedFalse(EmailType emailType, int userId);

    Optional<Email> findOneByEmailTypeAndUserId(EmailType emailType, int userId);


    //-----------------------------------------------------------------
    // Check if there's a token validated.
    //-----------------------------------------------------------------
    Optional<Email> findOneByEmailTypeAndUserIdAndTokenAndTokenValidatedTrue(EmailType emailType, int userId, String token);

    Optional<Email> findOneByEmailTypeAndUserIdAndTokenAndTokenValidatedFalse(EmailType emailType, String token, int userId);

    Optional<Email> findOneByEmailTypeAndUserIdAndToken(EmailType emailType, int userId, String token);

    //-----------------------------------------------------------------
    // Find all by type and user id
    //-----------------------------------------------------------------
    List<Email> findAllByEmailTypeAndUserId(EmailType emailType, int userId);

    //-----------------------------------------------------------------
    // By USER ID validated/non validated
    //-----------------------------------------------------------------
    Optional<Email> findOneByUserIdAndTokenValidatedTrue(int userId);

    Optional<Email> findOneByUserIdAndTokenValidatedFalse(int userId);

    //-----------------------------------------------------------------
    // All validated/non validated
    //-----------------------------------------------------------------
    List<Email> findAllByTokenValidatedTrue();

    List<Email> findAllByTokenValidatedFalse();

    @Modifying
    @Transactional
    @Query("delete from Email u where u.user.id = ?1")
    void removeByUserId(int userId);

    @Modifying
    @Transactional
    @Query("delete from Email u where u.emailType = ?1 and u.user.id = ?2")
    void deleteAllByEmailTypeAndUserId(EmailType emailType, int userId);
}