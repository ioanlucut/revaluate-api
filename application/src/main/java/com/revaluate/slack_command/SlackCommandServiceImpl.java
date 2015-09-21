package com.revaluate.slack_command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.google.common.base.Splitter;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.category.persistence.Category;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.expense.service.ExpenseService;
import com.revaluate.expense.service.ExpensesUtils;
import com.revaluate.slack.SlackException;
import com.revaluate.slack_command.commands.CommandAddExpense;
import com.revaluate.slack_command.commands.CommandCategories;
import com.revaluate.slack_command.commands.CommandHelp;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Validated
public class SlackCommandServiceImpl implements SlackCommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlackCommandServiceImpl.class);

    public static final int MAX_DESC_LENGTH = 100;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public String answer(@NotNull @Valid SlackDTO slackDTO, int userId) throws SlackException {
        LOGGER.info(String.format("SlackDTO: %s :", slackDTO));

        Splitter splitter = Splitter.on(' ').omitEmptyStrings().trimResults();

        List<String> tokens = splitter.splitToList(slackDTO.getText());

        if (tokens.isEmpty()) {
            throw new SlackException("Hey, what's up?");
        }

        String[] tokensAsArray = tokens.toArray(new String[tokens.size()]);

        JCommander jCommander = new JCommander();

        CommandAddExpense add = new CommandAddExpense();
        jCommander.addCommand("add", add);

        CommandCategories categories = new CommandCategories();
        jCommander.addCommand("categories", categories);

        CommandHelp help = new CommandHelp();
        jCommander.addCommand("help", help);

        try {
            jCommander.parse(tokensAsArray);
        } catch (ParameterException ex) {

            return getUsage(jCommander);
        }

        if (StringUtils.isBlank(jCommander.getParsedCommand())) {

            return getUsage(jCommander);
        }

        String parsedCommand = jCommander.getParsedCommand();

        switch (parsedCommand) {
            case "help": {

                return getUsage(jCommander);
            }
            case "add": {

                if (add.getExpenseDetails().size() < 2) {

                    return getUsage(jCommander);
                }

                return handleAddExpense(userId, add.getExpenseDetails());
            }
            case "categories": {

                return String.format("You dispose of the following categories: \n %s", getCategoriesJoined(userId));
            }
            default: {
                return getUsage(jCommander);
            }
        }
    }

    private String handleAddExpense(int userId, List<String> expenseDetails) throws SlackException {
        ExpenseDTOBuilder expenseDTOBuilder = new ExpenseDTOBuilder();

        //-----------------------------------------------------------------
        // First token is expected as price
        //-----------------------------------------------------------------

        double price = getPrice(expenseDetails.stream().findFirst().orElse("The price is missing."));
        expenseDTOBuilder
                .withValue(price);

        //-----------------------------------------------------------------
        // Second is expected as category
        //-----------------------------------------------------------------
        CategoryDTO category = getCategory(userId, expenseDetails.get(1));
        expenseDTOBuilder
                .withCategory(category);


        //-----------------------------------------------------------------
        // In this case we have description
        //-----------------------------------------------------------------
        if (expenseDetails.size() > 2) {
            String description = expenseDetails.stream().skip(2).collect(Collectors.joining(" "));
            handleDescription(expenseDTOBuilder, description);
        }

        //-----------------------------------------------------------------
        // Set the spent date as now
        //-----------------------------------------------------------------
        expenseDTOBuilder
                .withSpentDate(LocalDateTime.now());

        ExpenseDTO buildExpenseDTO = expenseDTOBuilder.build();

        try {
            ExpenseDTO expenseDTO = expenseService.create(buildExpenseDTO, userId);
            User user = userRepository.findOneById(userId).orElseThrow(() -> new SlackException("Do we know you?"));

            String expenseAddedFormat = ":white_check_mark: Yay! Done adding _Spent %s ( %s ) on %s_ ! Checkout <https://www.revaluate.io/expenses|dashboard> for more details!";
            return String.format(expenseAddedFormat,
                    ExpensesUtils.format(BigDecimal.valueOf(expenseDTO.getValue()), user.getCurrency()),
                    expenseDTO.getDescription(),
                    ExpensesUtils.formatDate(expenseDTO.getSpentDate()));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new SlackException("Upps, we had a problem trying to save your expense.");
        }
    }

    private void handleDescription(ExpenseDTOBuilder expenseDTOBuilder, String description) throws SlackException {
        if (description.length() > MAX_DESC_LENGTH) {
            throw new SlackException("Description length is too big. It can be maximum: " + MAX_DESC_LENGTH);
        }

        expenseDTOBuilder
                .withDescription(description);
    }

    private CategoryDTO getCategory(int userId, String categoryCandidate) throws SlackException {
        Optional<Category> optionalCategory = categoryRepository.findOneByNameIgnoreCaseAndUserId(categoryCandidate, userId);

        if (!optionalCategory.isPresent()) {

            throw new SlackException(String.format("We do not recognize %s as category. You dispose only of: \n %s", categoryCandidate, getCategoriesJoined(userId)));
        }

        return dozerBeanMapper.map(optionalCategory.get(), CategoryDTO.class);
    }

    private String getCategoriesJoined(int userId) throws SlackException {
        List<CategoryDTO> allCategoriesFor = categoryService.findAllCategoriesFor(userId);

        return allCategoriesFor
                .stream()
                .map(categoryDTO -> String.format("_%s_", categoryDTO.getName()))
                .collect(Collectors.joining("\n, "));
    }

    private double getPrice(String price) throws SlackException {
        String priceMatch = getPriceMatch(price);

        try {
            return ExpensesUtils.parseCurrency(priceMatch).doubleValue();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new SlackException("The price format is wrong.");
        }
    }

    private String getPriceMatch(String text) throws SlackException {
        Pattern p = Pattern.compile("-?[\\d\\.]+");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return m.group();
        }

        throw new SlackException("No valid price was identified.");
    }

    private static String getUsage(JCommander jCommander) {
        StringBuilder sb = new StringBuilder();
        jCommander.usage(sb);

        return sb.toString();
    }


}