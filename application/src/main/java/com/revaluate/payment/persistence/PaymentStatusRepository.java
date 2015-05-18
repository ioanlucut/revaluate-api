package com.revaluate.payment.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Integer> {

    Optional<PaymentStatus> findOneByUserId(int userId);

    @Modifying
    @Transactional
    @Query("delete from PaymentStatus u where u.user.id = ?1")
    void removeByUserId(int userId);
}