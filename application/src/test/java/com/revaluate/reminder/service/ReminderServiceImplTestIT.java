package com.revaluate.reminder.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.reminder.ReminderDTO;
import com.revaluate.domain.reminder.ReminderDTOBuilder;
import com.revaluate.domain.reminder.ReminderType;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class ReminderServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private ReminderService reminderService;

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create reminder
        //-----------------------------------------------------------------
        ReminderDTO reminderDTO = new ReminderDTOBuilder()
                .withDueOnDate(LocalDateTime.now())
                .withRecurringRule(String.format("FREQ=%s;BYDAY=%s;BYHOUR=%s;COUNT=%s", Recur.WEEKLY, WeekDay.MO, 8, 1))
                .withReminderType(ReminderType.REMINDER)
                .build();
        ReminderDTO createdReminderDTO = reminderService.create(reminderDTO, createdUserDTO.getId());
        reminderDTO.setId(createdReminderDTO.getId());

        //-----------------------------------------------------------------
        // Assert created reminder is ok
        //-----------------------------------------------------------------
        assertThat(createdReminderDTO, is(notNullValue()));
        assertThat(createdReminderDTO.getId(), not(equalTo(0)));
        assertThat(createdReminderDTO.getReminderType(), equalTo(ReminderType.REMINDER));
        assertThat(createdReminderDTO.getDueOnDate(), equalTo(reminderDTO.getDueOnDate()));
        assertThat(createdReminderDTO.getCreatedDate(), notNullValue());
        assertThat(createdReminderDTO.getModifiedDate(), notNullValue());
        assertThat(createdReminderDTO.getSentCount(), equalTo(0));
        assertThat(createdReminderDTO.getSentDate(), nullValue());
    }

    @Test
    public void create_invalidReminderDTO_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create an invalid reminder
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        reminderService.create(new ReminderDTOBuilder().build(), createdUserDTO.getId());
    }
}