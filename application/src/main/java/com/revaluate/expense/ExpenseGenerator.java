package com.revaluate.expense;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.category.service.CategoryService;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.expense.persistence.ExpenseRepository;
import com.revaluate.expense.service.ExpenseService;
import com.revaluate.insights.service.InsightsUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.YearMonth;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * For localhost only.
 */
public class ExpenseGenerator {

    public static final String TEST_EMAIL = "ioan.lucut88@gmail.com";

    public static void main(String[] args) {
        System.setProperty(ConfigProperties.ENVIRONMENT, "local");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserRepository userRepository = (UserRepository) context.getBean("userRepository");
        ExpenseRepository expenseRepository = (ExpenseRepository) context.getBean("expenseRepository");
        CategoryService categoryService = (CategoryService) context.getBean("categoryServiceImpl");
        ExpenseService expenseService = (ExpenseService) context.getBean("expenseServiceImpl");

        LocalDateTime after = ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime("2014-05-01T00:00:00Z");
        LocalDateTime before = ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime("2015-08-01T00:00:00Z");

        List<YearMonth> yearMonths = InsightsUtils.yearMonthsBetween(after, before);
        User user = userRepository.findOneByEmailIgnoreCase(TEST_EMAIL).get();

        List<CategoryDTO> allCategoriesFor = categoryService.findAllCategoriesFor(user.getId());

        expenseRepository.removeByUserId(user.getId());

        Random r = new Random();
        double rangeMin = 100.0;
        double rangeMax = 5000.0;
        yearMonths
                .stream()
                .forEach(yearMonth -> {

                    IntStream.range(0, 5)
                            .forEach(value -> {
                                allCategoriesFor
                                        .stream()
                                        .forEach(categoryDTO -> {
                                            double randomSpentValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
                                            ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(new BigDecimal(randomSpentValue).setScale(2, RoundingMode.HALF_UP).doubleValue()).withDescription("my first expense").withCategory(categoryDTO).withSpentDate(yearMonth.toLocalDate(1).toDateTimeAtStartOfDay().toLocalDateTime().plusHours(3)).build();
                                            try {
                                                expenseService.create(expenseDTO, user.getId());
                                            } catch (ExpenseException e) {
                                                e.printStackTrace();
                                            }
                                        });
                            });
                });

    }

}
