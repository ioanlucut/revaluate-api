package com.revaluate.insights.service;

import com.revaluate.category.persistence.Category;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.insights.*;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
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
    public InsightDTO fetchInsightAfterBeforePeriod(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before) {
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
        Comparator<TotalPerCategoryInsightDTO> totalPerCategoryInsightDTOComparator = (o1, o2) -> o1.getTotalAmount().compareTo(o2.getTotalAmount());
        List<TotalPerCategoryInsightDTO> totalPerCategoriesDTOs = groupedCategoriesEntrySet
                .stream()
                .map(categoryListEntry -> {
                    //-----------------------------------------------------------------
                    // Compute category DTO
                    //-----------------------------------------------------------------
                    CategoryDTO categoryDTO = dozerBeanMapper.map(categoryListEntry.getKey(), CategoryDTO.class);

                    //-----------------------------------------------------------------
                    // Compute total expenses of this category
                    //-----------------------------------------------------------------
                    BigDecimal bigDecimal = categoryListEntry
                            .getValue()
                            .stream()
                            .map(Expense::getValue)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    String totalAmount = decimalFormat.format(bigDecimal.setScale(DIGITS_SCALE, BigDecimal.ROUND_DOWN));
                    return new TotalPerCategoryInsightDTOBuilder()
                            .withCategoryDTO(categoryDTO)
                            .withTotalAmount(totalAmount)
                            .build();
                })
                .sorted(totalPerCategoryInsightDTOComparator.reversed())
                .collect(Collectors.toList());

        BigDecimal totalExpenses = allExpenses
                .stream()
                .map(Expense::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new InsightDTOBuilder()
                .withFrom(after)
                .withTo(before)
                .withNumberOfTransactions(allExpenses.size())
                .withTotalNumberOfTransactions(expenseRepository.countByUserId(userId))
                .withTotalAmountSpent(totalExpenses.doubleValue())
                .withTotalPerCategoryInsightDTOs(totalPerCategoriesDTOs)
                .build();
    }

    @Override
    public SummaryInsightsDTO computeSummaryInsights(int userId) {
        Optional<Expense> oneByUserIdOrderBySpentDateAsc = expenseRepository.findFirstByUserIdOrderBySpentDateAsc(userId);
        Optional<Expense> oneByUserIdOrderBySpentDateDesc = expenseRepository.findFirstByUserIdOrderBySpentDateDesc(userId);

        if (oneByUserIdOrderBySpentDateAsc.isPresent() && oneByUserIdOrderBySpentDateDesc.isPresent()) {

            return new SummaryInsightsDTOBuilder()
                    .withFirstExistingExpenseDate(oneByUserIdOrderBySpentDateAsc.get().getSpentDate())
                    .withLastExistingExpenseDate(oneByUserIdOrderBySpentDateDesc.get().getSpentDate())
                    .build();
        }

        return new SummaryInsightsDTOBuilder()
                .withFirstExistingExpenseDate(LocalDateTime.now())
                .withLastExistingExpenseDate(LocalDateTime.now())
                .build();
    }
}
