package com.revaluate.goals.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.goal.GoalDTO;
import com.revaluate.domain.goal.GoalDTOBuilder;
import com.revaluate.domain.goal.GoalTarget;
import com.revaluate.goals.exception.GoalException;
import org.joda.money.CurrencyUnit;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class GoalServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private GoalService goalService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(100.00)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Assert created goal is ok
        //-----------------------------------------------------------------
        assertThat(createdGoalDTO, is(notNullValue()));
        assertThat(createdGoalDTO.getId(), not(equalTo(0)));
        assertThat(createdGoalDTO.getCategory(), equalTo(goalDTO.getCategory()));
        assertThat(createdGoalDTO.getGoalTarget(), equalTo(GoalTarget.MORE_THAN));
        assertThat(createdGoalDTO.getValue(), equalTo(goalDTO.getValue()));
        assertThat(createdGoalDTO.getStartDate().getYear(), equalTo(goalDTO.getStartDate().getYear()));
        assertThat(createdGoalDTO.getEndDate().getYear(), equalTo(goalDTO.getEndDate().getYear()));
    }

    @Test
    public void create_testMaxAndMinValues_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(0.01)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        goalService.create(goalDTO, createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        goalDTO = new GoalDTOBuilder()
                .withValue(0.001)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());

        goalDTO = new GoalDTOBuilder()
                .withValue(111_111_111_111_111_111_11.11)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue(), equalTo(goalDTO.getValue()));

        goalDTO = new GoalDTOBuilder()
                .withValue(899_999_999_999_999_999_99.11)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue(), equalTo(goalDTO.getValue()));

        exception.expect(ConstraintViolationException.class);
        goalDTO = new GoalDTOBuilder()
                .withValue(999_999_999_999_999_999_99.11)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();

        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue(), equalTo(goalDTO.getValue()));
    }

    @Test
    public void create_withDifferentPriceCombinations_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        GoalDTO goalDTO;
        GoalDTO createdGoalDTO;

        //-----------------------------------------------------------------
        // Create goals
        //-----------------------------------------------------------------
        goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue(), equalTo(goalDTO.getValue()));

        goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue(), equalTo(goalDTO.getValue()));

        goalDTO = new GoalDTOBuilder()
                .withValue(2147483647)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue(), equalTo(goalDTO.getValue()));

        goalDTO = new GoalDTOBuilder()
                .withValue(2147483647.99)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue(), equalTo(goalDTO.getValue()));
    }

    @Test
    public void create_twoGoalsWithSameCategory_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create two goals
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();

        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        GoalDTO secondCreatedGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert created goals is ok
        //-----------------------------------------------------------------
        assertThat(createdGoalDTO.getCategory(), equalTo(goalDTO.getCategory()));
        assertThat(secondCreatedGoalDTO.getCategory(), equalTo(goalDTO.getCategory()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_withoutCategoryForTwoGoals_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create an invalid goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();

        goalService.create(goalDTO, createdUserDTO.getId());
    }

    @Test(expected = GoalException.class)
    public void create_properCategoryButWrongUserId_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create an invalid goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        goalService.create(goalDTO, createdUserDTO.getId() + 999999);
    }

    @Test
    public void create_invalidGoalDTO_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create an invalid goal - no category
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        goalService.create(new GoalDTOBuilder().withValue(2.55).build(), createdUserDTO.getId());
    }

    @Test(expected = GoalException.class)
    public void create_withAnotherUserCategory_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO firstUserDTO = createUserDTO(TEST_EMAIL);

        //-----------------------------------------------------------------
        // Create second user
        //-----------------------------------------------------------------
        UserDTO secondUserDTO = createUserDTO("xxx@xxx.xxx2", CurrencyUnit.AUD.getCurrencyCode());

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, secondUserDTO.getId());

        //-----------------------------------------------------------------
        // Create an goals with category from another user
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        goalService.create(goalDTO, firstUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Test update part
    //-----------------------------------------------------------------
    /*@Test
    public void update_value_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - ONLY VALUE
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder().withId(goalDTO.getId()).withValue(2.56).withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        GoalDTO updatedGoalDTO = goalService.update(goalDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated goal is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedGoalDTO.getId(), is(equalTo(createdGoalDTO.getId())));
        assertThat(updatedGoalDTO.getCategory().getColor(), equalTo(createdGoalDTO.getCategory().getColor()));
        assertThat(updatedGoalDTO.getCategory().getName(), equalTo(createdGoalDTO.getCategory().getName()));
        assertThat(updatedGoalDTO.getCategory().getId(), equalTo(createdGoalDTO.getCategory().getId()));
        // Only this was updated
        assertThat(updatedGoalDTO.getValue(), equalTo(goalDTOToUpdate.getValue()));
    }

    @Test
    public void update_description_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - ONLY description
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder().withId(goalDTO.getId()).withValue(2.55).withDescription("DESC").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        GoalDTO updatedGoalDTO = goalService.update(goalDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated goal is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedGoalDTO.getId(), is(equalTo(createdGoalDTO.getId())));
        assertThat(updatedGoalDTO.getCategory().getColor(), equalTo(createdGoalDTO.getCategory().getColor()));
        assertThat(updatedGoalDTO.getCategory().getName(), equalTo(createdGoalDTO.getCategory().getName()));
        assertThat(updatedGoalDTO.getCategory().getId(), equalTo(createdGoalDTO.getCategory().getId()));
        assertThat(updatedGoalDTO.getValue(), equalTo(createdGoalDTO.getValue()));
        // Only these was updated
        assertThat(updatedGoalDTO.getDescription(), equalTo(goalDTOToUpdate.getDescription()));
    }

    @Test
    public void update_category_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Create new category
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - ONLY description
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder().withValue(2.55).withId(goalDTO.getId()).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        GoalDTO updatedGoalDTO = goalService.update(goalDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated goal is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedGoalDTO.getId(), is(equalTo(createdGoalDTO.getId())));
        assertThat(updatedGoalDTO.getValue(), equalTo(createdGoalDTO.getValue()));
        assertThat(updatedGoalDTO.getDescription(), equalTo(goalDTOToUpdate.getDescription()));
        // Only these was updated
        assertThat(updatedGoalDTO.getCategory().getColor(), equalTo(createdCategoryDTO.getColor()));
        assertThat(updatedGoalDTO.getCategory().getName(), equalTo(createdCategoryDTO.getName()));
        assertThat(updatedGoalDTO.getCategory().getId(), equalTo(createdCategoryDTO.getId()));
    }

    @Test(expected = GoalException.class)
    public void update_wrongCategory_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - with category without ID
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder().withValue(2.55).withId(goalDTO.getId()).withDescription("my first goal").withSpentDate(LocalDateTime.now()).withCategory(categoryDTO).build();
        goalService.update(goalDTOToUpdate, createdUserDTO.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void update_noCategory_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - with category without ID
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder().withValue(2.55).withId(goalDTO.getId()).withDescription("my first goal").build();
        goalService.update(goalDTOToUpdate, createdUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Find all goals work
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUser_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created epenses + asserts
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsFor(createdUserDTO.getId());
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(2)));
    }

    @Test
    public void findAll_ofExistingUserIfNoGoalExist_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Find created epenses + asserts
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsFor(createdUserDTO.getId());
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(0)));
    }

    //-----------------------------------------------------------------
    // Find all goals work - AFTER - BEFORE
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserBetweenTwoDates_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        LocalDateTime after = LocalDateTime.now();

        //-----------------------------------------------------------------
        // Find created epenses + asserts
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsAfterBefore(createdUserDTO.getId(), before, after);
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(7)));
    }

    @Test
    public void findAllGoalsWithCategoryIdAfterBefore_ofExistingUserBetweenTwoDatesAndACategory_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO createdCategoryDTO = categoryService.create(new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create second category
        //-----------------------------------------------------------------
        CategoryDTO secondCreatedCategoryDTO = categoryService.create(new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        GoalDTO goalDTOOfTheSecondCategory = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(secondCreatedCategoryDTO).withSpentDate(LocalDateTime.now()).build();

        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTOOfTheSecondCategory, createdUserDTO.getId());
        goalService.create(goalDTOOfTheSecondCategory, createdUserDTO.getId());
        goalService.create(goalDTOOfTheSecondCategory, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        LocalDateTime after = LocalDateTime.now();

        //-----------------------------------------------------------------
        // Find created goals + asserts (should be only with the first category)
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsWithCategoryIdAfterBefore(createdUserDTO.getId(), createdCategoryDTO.getId(), before, after);
        allGoalsFor.stream().forEach(goalDTOCandidate -> assertThat(goalDTOCandidate.getCategory().getName(), is(equalTo(createdCategoryDTO.getName()))));
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(7)));
        assertThat(goalService.findAllGoalsAfterBefore(createdUserDTO.getId(), before, after).size(), is(equalTo(10)));
    }

    //-----------------------------------------------------------------
    // Find all goals work - AFTER a date
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserBetweenAfterADate_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        LocalDateTime startDate = LocalDateTime.now().minusSeconds(1);
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created epenses + asserts after a date
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsAfter(createdUserDTO.getId(), startDate);
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(7)));
    }

    //-----------------------------------------------------------------
    // Find all goals work - BEFORE a date
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserBetweenBeforeADate_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        LocalDateTime endDate = LocalDateTime.now();

        //-----------------------------------------------------------------
        // Find created epenses + asserts after a date
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsBefore(createdUserDTO.getId(), endDate);
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(7)));
    }

    //-----------------------------------------------------------------
    // Find all goals work - With a category id
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserWithCategoryId_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        CategoryDTO categoryDTOTwo = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build();
        CategoryDTO createdCategoryDTOTwo = categoryService.create(categoryDTOTwo, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        LocalDateTime endDate = LocalDateTime.now();
        goalDTO = new GoalDTOBuilder().withValue(2.55).withDescription("my first goal").withCategory(createdCategoryDTOTwo).withSpentDate(LocalDateTime.now()).build();
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created epenses + asserts after a date
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsBefore(createdUserDTO.getId(), endDate);
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(2)));
    }*/
}