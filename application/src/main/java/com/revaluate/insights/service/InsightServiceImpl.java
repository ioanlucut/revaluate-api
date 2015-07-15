package com.revaluate.insights.service;

import com.revaluate.category.persistence.Category;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.insights.*;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
public class InsightServiceImpl implements InsightService {

    public static final int DIGITS_SCALE = 2;
    public static final String PATTERN = "0.00";

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public InsightDTO fetchInsightAfterBeforePeriod(int userId, LocalDateTime after, LocalDateTime before) {
        List<Expense> allExpenses = expenseRepository.findAllByUserIdAndSpentDateAfterAndSpentDateBefore(userId, after, before);

        //-----------------------------------------------------------------
        // No results, return empty insight
        //-----------------------------------------------------------------
        if (allExpenses.isEmpty()) {
            return new InsightDTOBuilder()
                    .withFrom(after)
                    .withTo(before)
                    .withTotalPerCategoryInsightDTOs(Collections.emptyList())
                    .withTotalNumberOfTransactions(expenseRepository.countByUserId(userId))
                    .build();
        }

        //-----------------------------------------------------------------
        // Otherwise, return a nice insight
        //-----------------------------------------------------------------

        Set<Map.Entry<Category, List<Expense>>> groupedCategoriesEntrySet = allExpenses
                .stream()
                .collect(Collectors.groupingBy(Expense::getCategory))
                .entrySet();

        DecimalFormat decimalFormat = new DecimalFormat(PATTERN);

        //-----------------------------------------------------------------
        // Total per categories
        //-----------------------------------------------------------------
        Comparator<TotalPerCategoryInsightDTO> totalPerCategoryInsightDTOComparator = (o1, o2) -> Double.compare(o1.getTotalAmount(), o2.getTotalAmount());
        List<TotalPerCategoryInsightDTO> totalPerCategoriesDTOs = groupedCategoriesEntrySet
                .stream()
                .map(categoryListEntry -> {
                    TotalPerCategoryInsightDTOBuilder totalPerCategoryInsightDTOBuilder = new TotalPerCategoryInsightDTOBuilder();

                    //-----------------------------------------------------------------
                    // Compute category DTO
                    //-----------------------------------------------------------------
                    CategoryDTO categoryDTO = dozerBeanMapper.map(categoryListEntry.getKey(), CategoryDTO.class);

                    //-----------------------------------------------------------------
                    // Compute total expenses of this category
                    //-----------------------------------------------------------------
                    BigDecimal totalAmount = categoryListEntry
                            .getValue()
                            .stream()
                            .map(Expense::getValue)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    //-----------------------------------------------------------------
                    // Format total amount
                    //-----------------------------------------------------------------
                    String totalAmountFormatted = decimalFormat.format(totalAmount.setScale(DIGITS_SCALE, BigDecimal.ROUND_DOWN));

                    //-----------------------------------------------------------------
                    // Compute biggest expense
                    //-----------------------------------------------------------------
                    if (categoryListEntry.getValue().size() > 0) {
                        Comparator<? super Expense> biggestExpenseComparator = (o1, o2) -> o1.getValue().compareTo(o2.getValue());
                        Expense expense = categoryListEntry
                                .getValue()
                                .stream()
                                .sorted(biggestExpenseComparator.reversed())
                                .findFirst()
                                .get();
                        ExpenseDTO biggestExpenseDTO = dozerBeanMapper.map(expense, ExpenseDTO.class);

                        totalPerCategoryInsightDTOBuilder.withBiggestExpense(biggestExpenseDTO);
                    }

                    return totalPerCategoryInsightDTOBuilder
                            .withCategoryDTO(categoryDTO)
                            .withTotalAmount(totalAmount.doubleValue())
                            .withTotalAmountFormatted(totalAmountFormatted)
                            .withNumberOfTransactions(categoryListEntry.getValue().size())
                            .build();
                })
                .sorted(totalPerCategoryInsightDTOComparator.reversed())
                .collect(Collectors.toList());

        BigDecimal totalExpenses = allExpenses
                .stream()
                .map(Expense::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //-----------------------------------------------------------------
        // Compute biggest expense overall
        //-----------------------------------------------------------------
        Comparator<TotalPerCategoryInsightDTO> biggestExpenseOverallComparator = (o1, o2) -> Double.compare(o1.getBiggestExpense().getValue(), o2.getBiggestExpense().getValue());
        Optional<TotalPerCategoryInsightDTO> biggestExpenseOverallOptional = totalPerCategoriesDTOs
                .stream()
                .filter(totalPerCategoryInsightDTO -> totalPerCategoryInsightDTO.getNumberOfTransactions() > 0)
                .sorted(biggestExpenseOverallComparator.reversed())
                .findFirst();

        //-----------------------------------------------------------------
        // Compute most number of transactions
        //-----------------------------------------------------------------
        Comparator<TotalPerCategoryInsightDTO> mostNumberOfTransactionsComparator = (o1, o2) -> Integer.compare(o1.getNumberOfTransactions(), o2.getNumberOfTransactions());
        Optional<TotalPerCategoryInsightDTO> mostNumberOfTransactionsOptional = totalPerCategoriesDTOs
                .stream()
                .filter(totalPerCategoryInsightDTO -> totalPerCategoryInsightDTO.getNumberOfTransactions() > 0)
                .sorted(mostNumberOfTransactionsComparator.reversed())
                .findFirst();

        //-----------------------------------------------------------------
        // Compute highest amount category
        //-----------------------------------------------------------------
        Comparator<TotalPerCategoryInsightDTO> highestAmountCategoryComparator = (o1, o2) -> Double.compare(o1.getTotalAmount(), o2.getTotalAmount());
        Optional<TotalPerCategoryInsightDTO> highestAmountCategoryOptional = totalPerCategoriesDTOs
                .stream()
                .filter(totalPerCategoryInsightDTO -> totalPerCategoryInsightDTO.getNumberOfTransactions() > 0)
                .sorted(highestAmountCategoryComparator.reversed())
                .findFirst();

        //-----------------------------------------------------------------
        // Return the insight DTO
        //-----------------------------------------------------------------
        return new InsightDTOBuilder()
                .withFrom(after)
                .withTo(before)
                .withNumberOfTransactions(allExpenses.size())
                .withTotalNumberOfTransactions(expenseRepository.countByUserId(userId))
                .withTotalAmountSpent(totalExpenses.doubleValue())
                .withTotalPerCategoryInsightDTOs(totalPerCategoriesDTOs)
                .withBiggestExpense(biggestExpenseOverallOptional.isPresent() ? biggestExpenseOverallOptional.get().getBiggestExpense() : null)
                .withCategoryWithTheMostTransactionsInsightsDTO(
                        mostNumberOfTransactionsOptional.isPresent()
                                ? new CategoryWithTheMostTransactionsInsightsDTOBuilder()
                                .withCategoryDTO(mostNumberOfTransactionsOptional.get().getCategoryDTO())
                                .withNumberOfTransactions(mostNumberOfTransactionsOptional.get().getNumberOfTransactions())
                                .build()
                                : null
                )
                .withHighestAmountCategory(highestAmountCategoryOptional.isPresent()
                        ? highestAmountCategoryOptional.get().getCategoryDTO()
                        : null)
                .build();
    }

}
