package com.revaluate.account.persistence;

import com.revaluate.domain.account.UserSubscriptionStatus;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByEmailIgnoreCase(String email);

    List<User> findByEmailIgnoreCase(String email);

    List<User> findAllByUserSubscriptionStatusAndEndTrialDateBefore(UserSubscriptionStatus userSubscriptionStatus, LocalDateTime before);
}