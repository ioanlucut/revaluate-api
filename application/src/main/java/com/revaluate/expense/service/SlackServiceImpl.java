package com.revaluate.expense.service;

import com.google.common.base.Splitter;
import com.revaluate.category.persistence.Category;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.expense.exception.ExpenseException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Validated
public class SlackServiceImpl implements SlackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlackServiceImpl.class);

    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
    }};
    public static final int MAX_DESC_LENGTH = 100;


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseService expenseService;


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

            Optional<String> dateFormat = determineDateFormat(thirdCandidate);
            if (!dateFormat.isPresent()) {
                handleDescription(expenseDTOBuilder, thirdCandidate);
                expenseDTOBuilder.withSpentDate(LocalDateTime.now());
            } else {
                handleDate(expenseDTOBuilder, thirdCandidate);
            }

        }

        ExpenseDTO buildExpenseDTO = expenseDTOBuilder.build();

        try {
            expenseService.create(buildExpenseDTO, userId);
        } catch (ExpenseException ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new SlackException("Upps, we had a problem trying to save your expense.");
        }

        return "Yay! Expense added! :white_check_mark: GO to <https://revaluate.io/expenses|dashboard> for details!";
    }

    private void handleDescription(ExpenseDTOBuilder expenseDTOBuilder, String description) throws SlackException {
        if (description.length() > MAX_DESC_LENGTH) {
            throw new SlackException("Description length is too big. It can be maximum: " + MAX_DESC_LENGTH);
        }
        expenseDTOBuilder.withDescription(description);
    }

    private void handleDate(ExpenseDTOBuilder expenseDTOBuilder, String dateCandidate) throws SlackException {
        Optional<String> dateFormat = determineDateFormat(dateCandidate);
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

            throw new SlackException(categoryPossibilities);
        }

        return dozerBeanMapper.map(optionalCategory.get(), CategoryDTO.class);
    }

    private double getPrice(String price) throws SlackException {
        String priceMatch = getPriceMatch(price);
        String filteredPrice = priceMatch.replaceAll(",", "");

        try {
            return Double.parseDouble(filteredPrice);
        } catch (NumberFormatException ex) {
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

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     *
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    public Optional<String> determineDateFormat(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return Optional.ofNullable(DATE_FORMAT_REGEXPS.get(regexp));
            }
        }
        return Optional.empty(); // Unknown format.
    }

}