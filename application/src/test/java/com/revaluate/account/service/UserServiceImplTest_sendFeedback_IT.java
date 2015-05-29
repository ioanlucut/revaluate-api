package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.Email;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.FeedbackDTO;
import com.revaluate.domain.account.FeedbackDTOBuilder;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.email.EmailType;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_sendFeedback_IT extends AbstractIntegrationTests {

    @Test
    public void sendFeedback_happyFlow_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Send feedback
        FeedbackDTO feedbackDTO = new FeedbackDTOBuilder().withMessage("message").withSubject("subject").build();
        userService.sendFeedback(feedbackDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert that reset email token is added
        //-----------------------------------------------------------------
        User foundUser = userRepository.findOne(createdUserDTO.getId());
        List<Email> emails = emailRepository.findAllByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, foundUser.getId());
        assertThat(emails.size(), is(1));
    }

    @Test
    public void sendFeedback_validationWorks_throwsException() throws UserException {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        exception.expect(ConstraintViolationException.class);
        userService.sendFeedback(null, 0);

        exception.expect(UserException.class);
        FeedbackDTO feedbackDTO = new FeedbackDTOBuilder().withMessage("message").withSubject("subject").build();
        userService.sendFeedback(feedbackDTO, 0);

        exception.expect(ConstraintViolationException.class);
        feedbackDTO = new FeedbackDTOBuilder().withSubject("subject").build();
        userService.sendFeedback(feedbackDTO, createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        feedbackDTO = new FeedbackDTOBuilder().withMessage("message").build();
        userService.sendFeedback(feedbackDTO, createdUserDTO.getId());
    }
}
