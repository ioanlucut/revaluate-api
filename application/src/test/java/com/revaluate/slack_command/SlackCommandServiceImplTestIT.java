package com.revaluate.slack_command;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.domain.slack.SlackDTOBuilder;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import com.revaluate.slack.SlackException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class SlackCommandServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SlackCommandService slackCommandService;

    public static final String USAGE = "Usage: Revaluate [options] [command] [command options]\n" +
            "  Commands:\n" +
            "    add      Add expense\n" +
            "      Usage: add [options] Expense details with format [price] [category] [description]\n" +
            "\n" +
            "    categories      Categories\n" +
            "      Usage: categories [options]\n" +
            "\n" +
            "    help      Help\n" +
            "      Usage: help [options]";

    public static final String USAGE_BAD_ATTEMPT = "The format is wrong. \n\n" +
            "Usage: Revaluate [options] [command] [command options]\n" +
            "  Commands:\n" +
            "    add      Add expense\n" +
            "      Usage: add [options] Expense details with format [price] [category] [description]\n" +
            "\n" +
            "    categories      Categories\n" +
            "      Usage: categories [options]\n" +
            "\n" +
            "    help      Help\n" +
            "      Usage: help [options]";

    @Test
    public void add_expenseHappyFlow_ok() throws Exception {
        UserDTO createdUserDTO = createUserWithCategory("name");

        SlackDTO request = buildDummyRequestWithText("add 123.22 name xx");
        String answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(":white_check_mark: Yay! Added: 12.322,00 € - name: xx")));

        request = buildDummyRequestWithText("add 123.22 name");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(":white_check_mark: Yay! Added: 12.322,00 € - name: _none_")));

        //-----------------------------------------------------------------
        // Assert created expense is ok
        //-----------------------------------------------------------------
        List<Expense> allByUserId = expenseRepository.findAllByUserId(createdUserDTO.getId());
        assertThat(allByUserId.size(), is(equalTo(2)));
    }

    @Test
    public void add_expense_nonHappyFlow_ok() throws Exception {
        UserDTO createdUserDTO = createUserWithCategory("name");

        SlackDTO request = buildDummyRequestWithText("add");
        String answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE_BAD_ATTEMPT)));

        request = buildDummyRequestWithText("add 42.22");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE_BAD_ATTEMPT)));

        request = buildDummyRequestWithText("add 42,22.2");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE_BAD_ATTEMPT)));

        request = buildDummyRequestWithText("add   ");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE_BAD_ATTEMPT)));

        request = buildDummyRequestWithText("notKnown   ");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE_BAD_ATTEMPT)));

        request = buildDummyRequestWithText("");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE_BAD_ATTEMPT)));

        request = buildDummyRequestWithText("help");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE)));

        request = buildDummyRequestWithText("help   ");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE)));

        request = buildDummyRequestWithText("help   ");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE)));

        exception.expect(SlackException.class);
        exception.expectMessage("Description length is too big. It can be maximum 100 characters.");
        request = buildDummyRequestWithText("add 42.22 name AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBB");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE)));
    }

    @Test
    public void add_expense_unknownCategory_nonHappyFlow_ok() throws Exception {
        UserDTO createdUserDTO = createUserWithCategory("name");

        exception.expect(SlackException.class);
        exception.expectMessage("We do not recognize NNN as category. You dispose only of: \n" +
                " name");
        SlackDTO request = buildDummyRequestWithText("add 42.22 NNN ABCDEFG");
        slackCommandService.answer(request, createdUserDTO.getId());
    }

    @Test
    public void categories_ok() throws Exception {
        UserDTO createdUserDTO = createUserWithCategory("name");

        SlackDTO request = buildDummyRequestWithText("categories");
        String answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo("You dispose of the following categories: \n" +
                "name")));

        request = buildDummyRequestWithText("categories asdadasdas");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE_BAD_ATTEMPT)));
    }

    @Test
    public void help_ok() throws Exception {
        UserDTO createdUserDTO = createUserWithCategory("name");

        SlackDTO request = buildDummyRequestWithText("help");
        String answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE)));

        request = buildDummyRequestWithText("help asdadasdas");
        answer = slackCommandService.answer(request, createdUserDTO.getId());
        assertThat(answer.trim(), is(equalTo(USAGE_BAD_ATTEMPT)));
    }

    public UserDTO createUserWithCategory(String categoryName) throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName(categoryName).build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        return createdUserDTO;
    }

    public static SlackDTO buildDummyRequestWithText(String text) {
        return new SlackDTOBuilder()
                .withToken("token")
                .withTeamId("teamId")
                .withTeamDomain("teamDomain")
                .withChannelId("channelId")
                .withChannelName("channelName")
                .withUserId("userId")
                .withUserName("userName")
                .withCommand("command")
                .withText(text)
                .build();

    }

}
