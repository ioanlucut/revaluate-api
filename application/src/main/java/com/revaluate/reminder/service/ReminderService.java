package com.revaluate.reminder.service;

import com.revaluate.domain.reminder.ReminderDTO;
import com.revaluate.reminder.exception.ReminderException;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.PageRequest;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

public interface ReminderService {

    int MIN_SIZE_LIST = 1;
    int MAX_SIZE_LIST = 1000;

    @NotNull
    ReminderDTO create(@Valid ReminderDTO reminderDTO, int userId) throws ReminderException;

    @NotNull
    ReminderDTO update(@Valid ReminderDTO reminderDTO, int userId) throws ReminderException;

    @NotNull
    List<ReminderDTO> findAllRemindersFor(int userId, Optional<PageRequest> pageRequest);

    @NotNull
    List<ReminderDTO> findAllRemindersAfter(int userId, LocalDateTime after);

    @NotNull
    List<ReminderDTO> findAllRemindersBefore(int userId, LocalDateTime before);

    @NotNull
    List<ReminderDTO> findAllRemindersAfterBefore(int userId, LocalDateTime after, LocalDateTime before);

    @NotNull
    List<ReminderDTO> bulkCreate(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<ReminderDTO> reminderDTOs, int userId) throws ReminderException;

    void bulkDelete(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<ReminderDTO> reminderDTOs, int userId) throws ReminderException;

    void remove(@Min(1) int reminderId, int userId) throws ReminderException;
}