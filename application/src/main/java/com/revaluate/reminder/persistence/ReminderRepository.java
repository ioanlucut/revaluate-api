package com.revaluate.reminder.persistence;

import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Integer> {

    Optional<Reminder> findOneByIdAndUserId(int reminderId, int userId);

    List<Reminder> findAllByUserId(int userId);

    Page<Reminder> findAllByUserId(int userId, Pageable pageable);

    @Query("SELECT e.dueOnDate FROM Reminder e WHERE e.user.id = ?1")
    List<LocalDateTime> selectExistingDueOnDates(int userId);

    long countByUserId(int userId);

    List<Reminder> findAllByUserIdAndDueOnDateAfter(int userId, LocalDateTime after);

    List<Reminder> findAllByUserIdAndDueOnDateBefore(int userId, LocalDateTime before);

    List<Reminder> findAllByUserIdAndDueOnDateAfterAndDueOnDateBefore(int userId, LocalDateTime after, LocalDateTime before);

    Optional<Reminder> findFirstByUserIdOrderByDueOnDateDesc(int userId);

    Optional<Reminder> findFirstByUserIdOrderByDueOnDateAsc(int userId);

    @Modifying
    @Transactional
    @Query("delete from Reminder u where u.user.id = ?1")
    void removeByUserId(int userId);
}