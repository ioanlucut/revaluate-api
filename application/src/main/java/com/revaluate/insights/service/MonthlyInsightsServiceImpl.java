package com.revaluate.insights.service;

import com.revaluate.category.persistence.Category;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.insights.monthly.*;
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
import java.util.stream.Stream;

@Service
@Validated
public class MonthlyInsightsServiceImpl implements MonthlyInsightsService {

    public static final int DIGITS_SCALE = 2;
    public static final String PATTERN = "0.00";

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public InsightsMonthlyDTO fetchMonthlyInsightsAfterBeforePeriod(int userId, LocalDateTime after, LocalDateTime before) {
        List<Expense> allExpenses = expenseRepository.findAllByUserIdAndSpentDateAfterAndSpentDateBefore(userId, after, before);
        List<CategoryDTO> categories = categoryService.findAllCategoriesFor(userId);

        return this.computeMonthlyInsightsAfterBeforePeriod(categories, allExpenses, after, before);
    }

    @Override
    public InsightsMonthlyDTO computeMonthlyInsightsAfterBeforePeriod(List<CategoryDTO> categories, List<Expense> allExpenses, LocalDateTime after, LocalDateTime before) {
        //-----------------------------------------------------------------
        // No results, return empty insight
        //-----------------------------------------------------------------
        if (allExpenses.isEmpty()) {
            return new InsightsMonthlyDTOBuilder()
                    .withFrom(after)
                    .withTo(before)
                    .withTotalPerCategoryInsightsDTOs(Collections.emptyList())
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
        Comparator<TotalPerCategoryInsightsDTO> totalPerCategoryInsightDTOComparator = (o1, o2) -> Double.compare(o1.getTotalAmount(), o2.getTotalAmount());
        List<TotalPerCategoryInsightsDTO> totalPerCategoriesDTOs = groupedCategoriesEntrySet
                .stream()
                .map(categoryListEntry -> {
                    TotalPerCategoryInsightsDTOBuilder totalPerCategoryInsightsDTOBuilder = new TotalPerCategoryInsightsDTOBuilder();

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

                        totalPerCategoryInsightsDTOBuilder.withBiggestExpense(biggestExpenseDTO);
                    }

                    return totalPerCategoryInsightsDTOBuilder
                            .withCategoryDTO(categoryDTO)
                            .withTotalAmount(totalAmount.doubleValue())
                            .withTotalAmountFormatted(totalAmountFormatted)
                            .withNumberOfTransactions(categoryListEntry.getValue().size())
                            .build();
                })
                .collect(Collectors.toList());

        //-----------------------------------------------------------------
        // Compute biggest expense overall
        //-----------------------------------------------------------------
        Comparator<TotalPerCategoryInsightsDTO> biggestExpenseOverallComparator = (o1, o2) -> Double.compare(o1.getBiggestExpense().getValue(), o2.getBiggestExpense().getValue());
        Optional<TotalPerCategoryInsightsDTO> biggestExpenseOverallOptional = totalPerCategoriesDTOs
                .stream()
                .filter(totalPerCategoryInsightDTO -> totalPerCategoryInsightDTO.getNumberOfTransactions() > 0)
                .sorted(biggestExpenseOverallComparator.reversed())
                .findFirst();

        //-----------------------------------------------------------------
        // Compute most number of transactions
        //-----------------------------------------------------------------
        Comparator<TotalPerCategoryInsightsDTO> mostNumberOfTransactionsComparator = (o1, o2) -> Integer.compare(o1.getNumberOfTransactions(), o2.getNumberOfTransactions());
        Optional<TotalPerCategoryInsightsDTO> mostNumberOfTransactionsOptional = totalPerCategoriesDTOs
                .stream()
                .filter(totalPerCategoryInsightDTO -> totalPerCategoryInsightDTO.getNumberOfTransactions() > 0)
                .sorted(mostNumberOfTransactionsComparator.reversed())
                .findFirst();

        //-----------------------------------------------------------------
        // Compute highest amount category
        //-----------------------------------------------------------------
        Comparator<TotalPerCategoryInsightsDTO> highestAmountCategoryComparator = (o1, o2) -> Double.compare(o1.getTotalAmount(), o2.getTotalAmount());
        Optional<TotalPerCategoryInsightsDTO> highestAmountCategoryOptional = totalPerCategoriesDTOs
                .stream()
                .filter(totalPerCategoryInsightsDTO -> totalPerCategoryInsightsDTO.getNumberOfTransactions() > 0)
                .sorted(highestAmountCategoryComparator.reversed())
                .findFirst();

        Stream<TotalPerCategoryInsightsDTO> emptyTotalPerCategoryInsightsDTOStream = categories
                .stream()
                .filter(categoryCandidate -> totalPerCategoriesDTOs
                        .stream()
                        .noneMatch(totalPerCategoryDTO -> totalPerCategoryDTO.getCategoryDTO().getId() == categoryCandidate.getId()))
                .map(category -> new TotalPerCategoryInsightsDTOBuilder()
                        .withCategoryDTO(category)
                        .withTotalAmountFormatted("0.00")
                        .build());

        List<TotalPerCategoryInsightsDTO> allCombined = Stream
                .concat(totalPerCategoriesDTOs.stream(), emptyTotalPerCategoryInsightsDTOStream)
                .sorted(totalPerCategoryInsightDTOComparator.reversed())
                .collect(Collectors.toList());

        //-----------------------------------------------------------------
        // Return the insight DTO
        //-----------------------------------------------------------------
        return new InsightsMonthlyDTOBuilder()
                .withFrom(after)
                .withTo(before)
                .withNumberOfTransactions(allExpenses.size())
                .withTotalAmountSpent(InsightsUtils.totalOf(allExpenses).doubleValue())
                .withTotalPerCategoryInsightsDTOs(allCombined)
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
