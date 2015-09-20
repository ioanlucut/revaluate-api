package com.revaluate.slack_command;

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
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

        ExpenseDTOBuilder expenseDTOBuilder = new ExpenseDTOBuilder();
        Splitter splitter = Splitter.on(' ').omitEmptyStrings().trimResults();
        List<String> tokens = splitter.splitToList(slackDTO.getText());

        if (tokens.isEmpty()) {
            throw new SlackException("Hey, what's up?");
        }

        if (tokens.size() == 1) {
            throw new SlackException("Sorry, but the format is not proper.");
        }

        // First is expected as price
        double price = getPrice(tokens.stream().findFirst().orElse("Hey, no price ?"));
        expenseDTOBuilder.withValue(price);

        // Second is expected as category
        CategoryDTO category = getCategory(userId, tokens.get(1));
        expenseDTOBuilder.withCategory(category);

        // In this case we can have description and date
        if (tokens.size() == 4) {
            handleDescription(expenseDTOBuilder, tokens.get(2));
            handleDate(expenseDTOBuilder, tokens.get(3));
        } else if (tokens.size() == 3) {
            // We do not know if this is description or date, but let's guess

            String thirdCandidate = tokens.get(2);

            Optional<String> dateFormat = ExpensesUtils.determineDateFormat(thirdCandidate);
            if (!dateFormat.isPresent()) {
                handleDescription(expenseDTOBuilder, thirdCandidate);
                expenseDTOBuilder.withSpentDate(LocalDateTime.now());
            } else {
                handleDate(expenseDTOBuilder, thirdCandidate);
            }
        } else {
            expenseDTOBuilder.withSpentDate(LocalDateTime.now());
        }

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
        expenseDTOBuilder.withDescription(description);
    }

    private void handleDate(ExpenseDTOBuilder expenseDTOBuilder, String dateCandidate) throws SlackException {
        Optional<String> dateFormat = ExpensesUtils.determineDateFormat(dateCandidate);
        if (!dateFormat.isPresent()) {
            throw new SlackException("Date format is not known");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat.get());
        LocalDateTime asLocalDate = dateTimeFormatter.withOffsetParsed().parseLocalDateTime(dateCandidate);
        expenseDTOBuilder.withSpentDate(asLocalDate);
    }

    private CategoryDTO getCategory(int userId, String categoryCandidate) throws SlackException {
        Optional<Category> optionalCategory = categoryRepository.findOneByNameIgnoreCaseAndUserId(categoryCandidate, userId);

        if (!optionalCategory.isPresent()) {
            List<CategoryDTO> allCategoriesFor = categoryService.findAllCategoriesFor(userId);

            String categoryPossibilities = allCategoriesFor
                    .stream()
                    .map(categoryDTO -> String.format("_%s_", categoryDTO.getName()))
                    .collect(Collectors.joining(", "));

            throw new SlackException(String.format("We do not recognize %s as category. You dispose only of: %s", categoryCandidate, categoryPossibilities));
        }

        return dozerBeanMapper.map(optionalCategory.get(), CategoryDTO.class);
    }

    private double getPrice(String price) throws SlackException {
        String priceMatch = getPriceMatch(price);

        try {
            return ExpensesUtils.parseCurrency(priceMatch).doubleValue();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new SlackException("Hey, the price format is wrong.");
        }
    }

    private String getPriceMatch(String text) throws SlackException {
        Pattern p = Pattern.compile("-?[\\d\\.]+");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return m.group();
        }

        throw new SlackException("No price found");
    }

}