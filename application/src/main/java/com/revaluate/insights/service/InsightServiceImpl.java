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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
                    .withInsightData(Collections.emptyList())
                    .withInsightColors(Collections.emptyList())
                    .withInsightLabels(Collections.emptyList())
                    .withTotalPerCategoryInsightDTOs(Collections.emptyList())
                    .build();
        }

        //-----------------------------------------------------------------
        // Otherwise, return a nice insight
        //-----------------------------------------------------------------
        Map<Category, List<Expense>> groupedByCategory = allExpenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory));

        List<String> insightsLabels = groupedByCategory.entrySet()
                .stream()
                .map(categoryListEntry -> categoryListEntry.getKey().getName())
                .collect(Collectors.toList());

        List<String> insightColors = groupedByCategory.entrySet()
                .stream()
                .map(categoryListEntry -> categoryListEntry.getKey().getColor())
                .collect(Collectors.toList());

        DecimalFormat decimalFormat = new DecimalFormat(PATTERN);

        //-----------------------------------------------------------------
        // Total per categories
        //-----------------------------------------------------------------
        List<TotalPerCategoryInsightDTO> totalPerCategoriesDTOs = groupedByCategory.entrySet()
                .stream()
                .map(categoryListEntry -> {
                    CategoryDTO categoryDTO = dozerBeanMapper.map(categoryListEntry.getKey(), CategoryDTO.class);
                    BigDecimal bigDecimal = categoryListEntry.getValue()
                            .stream()
                            .map(Expense::getValue)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    String totalAmount = decimalFormat.format(bigDecimal.setScale(DIGITS_SCALE, BigDecimal.ROUND_DOWN));
                    return new TotalPerCategoryInsightDTOBuilder()
                            .withCategoryDTO(categoryDTO)
                            .withTotalAmount(totalAmount)
                            .build();
                })
                .collect(Collectors.toList());

        List<String> insightData = groupedByCategory.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(expenses -> expenses.stream()
                        .map(Expense::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .map(bigDecimal -> decimalFormat.format(bigDecimal.setScale(DIGITS_SCALE, BigDecimal.ROUND_DOWN)))
                .collect(Collectors.toList());

        BigDecimal totalExpenses = allExpenses
                .stream()
                .map(Expense::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        SummaryInsightsDTO summaryInsightsDTO = computeSummaryInsights(userId);

        return new InsightDTOBuilder()
                .withFrom(after)
                .withTo(before)
                .withInsightData(insightData)
                .withInsightColors(insightColors)
                .withInsightLabels(insightsLabels)
                .withNumberOfTransactions(allExpenses.size())
                .withTotalAmountSpent(totalExpenses.doubleValue())
                .withTotalPerCategoryInsightDTOs(totalPerCategoriesDTOs)
                .withSummaryInsightsDTO(summaryInsightsDTO)
                .build();
    }

    @Override
    public SummaryInsightsDTO computeSummaryInsights(int userId) {
        Optional<Expense> oneByUserIdOrderBySpentDateAsc = expenseRepository.findFirstByUserIdOrderBySpentDateAsc(userId);
        Optional<Expense> oneByUserIdOrderBySpentDateDesc = expenseRepository.findFirstByUserIdOrderBySpentDateDesc(userId);

        if (oneByUserIdOrderBySpentDateAsc.isPresent() || oneByUserIdOrderBySpentDateDesc.isPresent()) {

            return new SummaryInsightsDTOBuilder()
                    .withFirstExistingExpenseDate(LocalDateTime.now())
                    .withLastExistingExpenseDate(LocalDateTime.now())
                    .build();
        }

        return new SummaryInsightsDTOBuilder()
                .withFirstExistingExpenseDate(oneByUserIdOrderBySpentDateAsc.get().getSpentDate())
                .withLastExistingExpenseDate(oneByUserIdOrderBySpentDateDesc.get().getSpentDate())
                .build();
    }
}
