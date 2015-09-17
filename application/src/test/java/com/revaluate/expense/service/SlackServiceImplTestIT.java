package com.revaluate.expense.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.domain.slack.SlackDTOBuilder;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class SlackServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SlackService slackService;

    @Test
    public void create_expenseThroughSlack_ok() throws Exception {

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        SlackDTO request = new SlackDTOBuilder()
                .withToken("token")
                .withTeamId("teamId")
                .withTeamDomain("teamDomain")
                .withChannelId("channelId")
                .withChannelName("channelName")
                .withUserId("userId")
                .withUserName("userName")
                .withCommand("command")
                .withText("123.22 name xx")
                .build();

        String answer = slackService.answer(request, userDTO.getId());

        //-----------------------------------------------------------------
        // Assert created expense is ok
        //-----------------------------------------------------------------
        assertThat(answer, is(notNullValue()));
        List<Expense> allByUserId = expenseRepository.findAllByUserId(userDTO.getId());
        assertThat(allByUserId.size(), is(equalTo(1)));

        System.out.println(allByUserId);
    }

    @Test
    public void create_expenseThroughSlackWithYear_ok() throws Exception {

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        SlackDTO request = new SlackDTOBuilder()
                .withToken("token")
                .withTeamId("teamId")
                .withTeamDomain("teamDomain")
                .withChannelId("channelId")
                .withChannelName("channelName")
                .withUserId("userId")
                .withUserName("userName")
                .withCommand("command")
                .withText("123.22 name xx 10-07-1988")
                .build();

        String answer = slackService.answer(request, userDTO.getId());

        //-----------------------------------------------------------------
        // Assert created expense is ok
        //-----------------------------------------------------------------
        assertThat(answer, is(notNullValue()));
        List<Expense> allByUserId = expenseRepository.findAllByUserId(userDTO.getId());
        assertThat(allByUserId.size(), is(equalTo(1)));

        System.out.println(allByUserId);
    }
}
