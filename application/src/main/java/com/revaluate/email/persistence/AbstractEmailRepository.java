package com.revaluate.email.persistence;

import com.revaluate.domain.email.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractEmailRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    Optional<T> findOneByEmailTypeAndUserId(EmailType emailType, int userId);

    //-----------------------------------------------------------------
    // Find all by type and user id
    //-----------------------------------------------------------------
    List<T> findAllByEmailTypeAndUserId(EmailType emailType, int userId);

    @Modifying
    @Transactional
    @Query("delete from Email u where u.user.id = ?1")
    void removeByUserId(int userId);

    @Modifying
    @Transactional
    @Query("delete from Email u where u.emailType = ?1 and u.user.id = ?2")
    void deleteAllByEmailTypeAndUserId(EmailType emailType, int userId);
}