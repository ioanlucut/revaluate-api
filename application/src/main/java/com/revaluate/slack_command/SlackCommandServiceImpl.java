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
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.expense.service.ExpenseService;
import com.revaluate.expense.service.ExpensesUtils;
import com.revaluate.slack.SlackException;
import com.revaluate.slack_command.commands.CommandAddExpense;
import com.revaluate.slack_command.commands.CommandCategories;
import com.revaluate.slack_command.commands.CommandHelp;
import com.revaluate.slack_command.commands.CommandListExpenses;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
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

    public static final String COMMANDS_USAGE = "%s, your available commands for Revaluate:\n" +
            "- Add an expense:\n" +
            "/revaluate add <price> <CATEGORY> [<description>]\n" +
            "\n" +
            "- List available categories:\n" +
            "/revaluate categories\n" +
            "\n" +
            "- List available expenses:\n" +
            "/revaluate list [-cat <CATEGORY>] [-limit <LIMIT>]\n" +
            "\n" +
            "- Get help (this message):\n" +
            "/revaluate help";

    public static final String ADD_USAGE = "I couldn't figure out what you meant. Please enter expenses in the form: \n" +
            "/revaluate add 43 FOOD going out\n" +
            "\n" +
            "Or /revaluate help";

    //-----------------------------------------------------------------
    // Other constants
    //-----------------------------------------------------------------
    public static final String EXPENSE_SPENT_DATE_COLUMN = "spentDate";
    public static final String EXPENSE_CREATED_DATE_COLUMN = "createdDate";
    public static final int DEFAULT_LIMIT = 10;
    public static final int DEFAULT_PAGE = 0;
    private static final Integer MAX_SIZE = 100;

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

        JCommander jCommander = new JCommander();
        jCommander.setProgramName("Revaluate");

        CommandAddExpense add = new CommandAddExpense();
        jCommander.addCommand("add", add);

        CommandCategories categories = new CommandCategories();
        jCommander.addCommand("categories", categories);

        CommandHelp help = new CommandHelp();
        jCommander.addCommand("help", help);

        CommandListExpenses list = new CommandListExpenses();
        jCommander.addCommand("list", list);

        if (tokens.isEmpty()) {
            return getUsage(Boolean.TRUE);
        }

        String[] tokensAsArray = tokens.toArray(new String[tokens.size()]);

        try {
            jCommander.parse(tokensAsArray);
        } catch (ParameterException ex) {

            return getUsage(Boolean.TRUE);
        }

        if (StringUtils.isBlank(jCommander.getParsedCommand())) {

            return getUsage(Boolean.TRUE);
        }

        String parsedCommand = jCommander.getParsedCommand();

        switch (parsedCommand) {
            case "help": {

                return getUsage(Boolean.FALSE);
            }
            case "add": {

                if (add.getExpenseDetails().size() < 2) {

                    return ADD_USAGE;
                }

                return handleAddExpense(userId, add.getExpenseDetails());
            }
            case "categories": {

                return String.format("You dispose of the following categories: \n%s", getCategoriesJoined(userId));
            }
            case "list": {

                return handleList(userId, list);
            }
            default: {
                return getUsage(Boolean.TRUE);
            }
        }
    }

    private String handleAddExpense(int userId, List<String> expenseDetails) throws SlackException {
        ExpenseDTOBuilder expenseDTOBuilder = new ExpenseDTOBuilder();

        //-----------------------------------------------------------------
        // First token is expected as price
        //-----------------------------------------------------------------

        double price = getPrice(expenseDetails.get(0));
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
            return createAndGet(userId, buildExpenseDTO);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new SlackException("Upps, we had a problem trying to save your expense.");
        }
    }

    private String createAndGet(int userId, ExpenseDTO buildExpenseDTO) throws SlackException, ExpenseException, ParseException {
        User user = userRepository
                .findOneById(userId)
                .orElseThrow(() -> new SlackException("We couldn't recognize your credentials. You can authenticate at <https://www.revaluate.io|revaluate.io>"));
        ExpenseDTO expenseDTO = expenseService.create(buildExpenseDTO, userId);

        return ExpensesUtils.formatExpenseFrom(user, expenseDTO, ExpensesUtils.ExpenseDisplayType.ADD);
    }

    private void handleDescription(ExpenseDTOBuilder expenseDTOBuilder, String description) throws SlackException {
        if (description.length() > MAX_DESC_LENGTH) {
            throw new SlackException(String.format("Description length is too big. It can be maximum %s characters.", MAX_DESC_LENGTH));
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
                .map(categoryDTO -> String.format("%s", categoryDTO.getName()))
                .collect(Collectors.joining("\n"));
    }

    private double getPrice(String price) throws SlackException {
        String priceMatch = getPriceMatch(price);

        try {
            return ExpensesUtils.parseCurrency(priceMatch).doubleValue();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new SlackException("Please check the price, something seems odd.");
        }
    }

    private String getPriceMatch(String text) throws SlackException {
        Pattern p = Pattern.compile("-?[\\d\\.]+");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return m.group();
        }

        throw new SlackException("Please check the price, something seems odd.");
    }

    private static String getUsage(boolean badAttempt) {
        StringBuilder sb = new StringBuilder();
        if (badAttempt) {
            sb.append("Please check the command, something seems odd.\n\n");
        }

        sb.append(String.format(COMMANDS_USAGE, badAttempt ? "Anyways" : "Hey"));

        return sb.toString();
    }

    private String handleList(int userId, CommandListExpenses list) throws SlackException {
        Integer size = Optional.ofNullable(list.getLimit()).orElse(DEFAULT_LIMIT);
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        } else if (size <= 0) {
            size = DEFAULT_LIMIT;
        }
        PageRequest pageRequest = new PageRequest(DEFAULT_PAGE, size, new Sort(
                new Sort.Order(Sort.Direction.DESC, EXPENSE_SPENT_DATE_COLUMN),
                new Sort.Order(Sort.Direction.DESC, EXPENSE_CREATED_DATE_COLUMN)
        ));

        if (StringUtils.isNotBlank(list.getCategory())) {
            Optional<Category> oneByNameIgnoreCaseAndUserId = categoryRepository.findOneByNameIgnoreCaseAndUserId(list.getCategory(), userId);

            if (oneByNameIgnoreCaseAndUserId.isPresent()) {
                Category category = oneByNameIgnoreCaseAndUserId.get();
                List<ExpenseDTO> expenseDTOs = expenseService.findAllExpensesOfCategoryFor(userId,
                        category.getId(),
                        Optional.of(pageRequest));

                return getExpensesJoined(userId, expenseDTOs);
            }

            return String.format("You dispose of the following categories: \n%s", getCategoriesJoined(userId));
        } else {
            return getExpensesJoined(userId, expenseService.findAllExpensesFor(userId, Optional.of(pageRequest)));
        }
    }

    private String getExpensesJoined(int userId, List<ExpenseDTO> expenseDTOs) throws SlackException {
        User user = userRepository
                .findOneById(userId)
                .orElseThrow(() -> new SlackException("We couldn't recognize your credentials. You can authenticate at <https://www.revaluate.io|revaluate.io>"));

        return expenseDTOs
                .stream()
                .map(expenseDTO -> ExpensesUtils.formatExpenseFrom(user, expenseDTO, ExpensesUtils.ExpenseDisplayType.LIST))
                .collect(Collectors.joining("\n"));
    }


}