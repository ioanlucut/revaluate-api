package com.revaluate.reminder.service;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.reminder.ReminderDTO;
import com.revaluate.reminder.exception.ReminderException;
import com.revaluate.reminder.persistence.Reminder;
import com.revaluate.reminder.persistence.ReminderRepository;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public ReminderDTO create(ReminderDTO reminderDTO, int userId) throws ReminderException {
        Reminder reminder = dozerBeanMapper.map(reminderDTO, Reminder.class);

        User foundUser = userRepository.findOne(userId);
        reminder.setUser(foundUser);
        Reminder savedReminder = reminderRepository.save(reminder);

        return dozerBeanMapper.map(savedReminder, ReminderDTO.class);
    }

    @Override
    public ReminderDTO update(ReminderDTO reminderDTO, int userId) throws ReminderException {
        Optional<Reminder> reminderById = reminderRepository.findOneByIdAndUserId(reminderDTO.getId(), userId);
        Reminder foundReminder = reminderById.orElseThrow(() -> new ReminderException("Reminder doesn't exist"));

        //-----------------------------------------------------------------
        // Update the reminder with given reminder DTO
        //-----------------------------------------------------------------
        dozerBeanMapper.map(reminderDTO, foundReminder);

        Reminder savedReminder = reminderRepository.save(foundReminder);
        return dozerBeanMapper.map(savedReminder, ReminderDTO.class);
    }

    @Override
    public List<ReminderDTO> findAllRemindersFor(int userId, Optional<PageRequest> pageRequest) {
        if (pageRequest.isPresent()) {
            Page<Reminder> reminders = reminderRepository.findAllByUserId(userId, pageRequest.get());

            return collectAndGet(reminders.getContent());
        }

        return collectAndGet(reminderRepository.findAllByUserId(userId));
    }

    @Override
    public List<ReminderDTO> findAllRemindersAfter(int userId, LocalDateTime after) {
        List<Reminder> reminders = reminderRepository.findAllByUserIdAndDueOnDateAfter(userId, after);

        return collectAndGet(reminders);
    }

    @Override
    public List<ReminderDTO> findAllRemindersBefore(int userId, LocalDateTime before) {
        List<Reminder> reminders = reminderRepository.findAllByUserIdAndDueOnDateBefore(userId, before);

        return collectAndGet(reminders);
    }

    @Override
    public List<ReminderDTO> findAllRemindersAfterBefore(int userId, LocalDateTime after, LocalDateTime before) {
        List<Reminder> reminders = reminderRepository.findAllByUserIdAndDueOnDateAfterAndDueOnDateBefore(userId, after, before);

        return collectAndGet(reminders);
    }

    @Override
    public List<ReminderDTO> bulkCreate(List<ReminderDTO> reminderDTOs, int userId) throws ReminderException {
        User foundUser = userRepository.findOne(userId);
        List<Reminder> reminders = reminderDTOs.stream()
                .map(reminderDTO -> {
                    Reminder reminder = dozerBeanMapper.map(reminderDTO, Reminder.class);
                    reminder.setUser(foundUser);

                    return reminder;
                })
                .collect(Collectors.toList());

        //-----------------------------------------------------------------
        // Save them
        //-----------------------------------------------------------------
        List<Reminder> savedReminders = reminderRepository.save(reminders);

        return savedReminders.stream()
                .map(reminderDTO -> dozerBeanMapper.map(reminderDTO, ReminderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void bulkDelete(List<ReminderDTO> reminderDTOs, int userId) throws ReminderException {
        //-----------------------------------------------------------------
        // Reminders have to exist for this user.
        //-----------------------------------------------------------------
        if (!reminderDTOs.stream().allMatch(reminderDTO -> reminderRepository.findOneByIdAndUserId(reminderDTO.getId(), userId).isPresent())) {
            throw new ReminderException("One or more reminder is invalid.");
        }

        List<Reminder> reminders = reminderDTOs.stream()
                .map(reminderDTO -> reminderRepository.findOneByIdAndUserId(reminderDTO.getId(), userId).get())
                .collect(Collectors.toList());

        reminderRepository.delete(reminders);
    }

    @Override
    public void remove(int reminderId, int userId) throws ReminderException {
        Optional<Reminder> reminderById = reminderRepository.findOneByIdAndUserId(reminderId, userId);
        reminderById.orElseThrow(() -> new ReminderException("Reminder doesn't exist"));

        reminderRepository.delete(reminderId);
    }

    private List<ReminderDTO> collectAndGet(List<Reminder> reminders) {
        return reminders.stream().map(category -> dozerBeanMapper.map(category, ReminderDTO.class)).collect(Collectors.toList());
    }
}