package com.revaluate.expense.service;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.category.persistence.Category;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.domain.expense.*;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public ExpenseDTO create(ExpenseDTO expenseDTO, int userId) throws ExpenseException {
        Expense expense = dozerBeanMapper.map(expenseDTO, Expense.class);
        Optional<Category> categoryByName = categoryRepository.findOneByIdAndUserId(expense.getCategory().getId(), userId);
        Category categoryFound = categoryByName.orElseThrow(() -> new ExpenseException("Category doesn't exist"));

        User foundUser = userRepository.findOne(userId);
        expense.setUser(foundUser);
        expense.setCategory(categoryFound);
        Expense savedExpense = expenseRepository.save(expense);

        return dozerBeanMapper.map(savedExpense, ExpenseDTO.class);
    }

    @Override
    public ExpenseDTO update(ExpenseDTO expenseDTO, int userId) throws ExpenseException {
        Optional<Expense> expenseById = expenseRepository.findOneByIdAndUserId(expenseDTO.getId(), userId);
        Expense foundExpense = expenseById.orElseThrow(() -> new ExpenseException("Expense doesn't exist"));

        if (!categoryRepository.findOneByIdAndUserId(expenseDTO.getCategory().getId(), userId).isPresent()) {
            throw new ExpenseException("This category does not exist");
        }

        //-----------------------------------------------------------------
        // Update the expense with given expense DTO
        //-----------------------------------------------------------------
        dozerBeanMapper.map(expenseDTO, foundExpense);

        Expense savedExpense = expenseRepository.save(foundExpense);
        return dozerBeanMapper.map(savedExpense, ExpenseDTO.class);
    }

    @Override
    public List<ExpenseDTO> findAllExpensesFor(int userId, Optional<PageRequest> pageRequest) {
        if (pageRequest.isPresent()) {
            Page<Expense> expenses = expenseRepository.findAllByUserId(userId, pageRequest.get());

            return mapToDTO(expenses.getContent());
        }

        return mapToDTO(expenseRepository.findAllByUserId(userId));
    }

    @Override
    public List<ExpenseDTO> findAllExpensesOfCategoryFor(int userId, int categoryId, Optional<PageRequest> pageRequest) {
        if (pageRequest.isPresent()) {
            Page<Expense> expenses = expenseRepository.findAllByUserIdAndCategoryId(userId, categoryId, pageRequest.get());

            return mapToDTO(expenses.getContent());
        }

        return mapToDTO(expenseRepository.findAllByUserIdAndCategoryId(userId, categoryId));
    }

    @Override
    public ExpensesQueryResponseDTO findExpensesOfCategoryGroupBySpentDateFor(int userId, int categoryId, PageRequest pageRequest) {
        Page<Expense> pageExpenses = expenseRepository.findAllByUserIdAndCategoryId(userId, categoryId, pageRequest);
        List<ExpenseDTO> allExpensesOf = mapToDTO(pageExpenses.getContent());

        return mapToExpensesQueryResponseDTO(pageRequest, allExpensesOf, pageExpenses.getTotalPages());
    }

    @Override
    public List<ExpenseDTO> findAllExpensesAfter(int userId, LocalDateTime after) {
        List<Expense> expenses = expenseRepository.findAllByUserIdAndSpentDateAfter(userId, after);

        return mapToDTO(expenses);
    }

    @Override
    public List<ExpenseDTO> findAllExpensesBefore(int userId, LocalDateTime before) {
        List<Expense> expenses = expenseRepository.findAllByUserIdAndSpentDateBefore(userId, before);

        return mapToDTO(expenses);
    }

    @Override
    public List<ExpenseDTO> findAllExpensesAfterBefore(int userId, LocalDateTime after, LocalDateTime before) {
        List<Expense> expenses = expenseRepository.findAllByUserIdAndSpentDateAfterAndSpentDateBefore(userId, after, before);

        return mapToDTO(expenses);
    }

    @Override
    public ExpensesQueryResponseDTO findExpensesGroupBySpentDate(int userId, PageRequest pageRequest) {
        List<ExpenseDTO> allExpensesOf = findAllExpensesFor(userId, Optional.of(pageRequest));

        return mapToExpensesQueryResponseDTO(pageRequest, allExpensesOf, allExpensesOf.size());
    }

    private List<GroupedExpensesDTO> groupBySpentDateAndCollect(List<ExpenseDTO> allExpensesAfterBefore) {
        Map<LocalDate, List<ExpenseDTO>> groupedExpenses = allExpensesAfterBefore
                .stream()
                .collect(Collectors
                        .groupingBy(expense -> LocalDate.fromDateFields(expense.getSpentDate().toDate())));

        return groupedExpenses
                .entrySet()
                .stream()
                .map(localDateListEntry ->
                        new GroupedExpensesDTOBuilder()
                                .withExpenseDTOs(localDateListEntry.getValue())
                                .withLocalDate(localDateListEntry.getKey())
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> findAllExpensesWithCategoryIdAfterBefore(int userId, int categoryId, LocalDateTime after, LocalDateTime before) {
        List<Expense> expenses = expenseRepository.findAllByUserIdAndCategoryIdAndSpentDateAfterAndSpentDateBefore(userId, categoryId, after, before);

        return mapToDTO(expenses);
    }

    @Override
    public List<ExpenseDTO> findAllExpensesWithCategoryIdFor(int categoryId, int userId) {
        List<Expense> expenses = expenseRepository.findAllByUserIdAndCategoryId(userId, categoryId);

        return mapToDTO(expenses);
    }

    @Override
    public List<ExpenseDTO> bulkCreate(List<ExpenseDTO> categoryDTOs, int userId) throws ExpenseException {
        User foundUser = userRepository.findOne(userId);
        List<Expense> categories = categoryDTOs.stream()
                .map(expenseDTO -> {
                    Expense expense = dozerBeanMapper.map(expenseDTO, Expense.class);
                    expense.setUser(foundUser);

                    return expense;
                })
                .collect(Collectors.toList());

        //-----------------------------------------------------------------
        // Save them
        //-----------------------------------------------------------------
        List<Expense> savedExpenses = expenseRepository.save(categories);

        return savedExpenses.stream()
                .map(expenseDTO -> dozerBeanMapper.map(expenseDTO, ExpenseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void bulkDelete(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<ExpenseDTO> expenseDTOs, int userId) {
        //-----------------------------------------------------------------
        // Expenses have to exist for this user.
        //-----------------------------------------------------------------
        List<Expense> expenses = expenseDTOs
                .stream()
                .map(expenseDTO -> {
                    Expense found = expenseRepository
                            .findOneByIdAndUserId(expenseDTO.getId(), userId)
                            .orElseThrow(() -> new ConstraintViolationException("One or more expense is invalid.", new HashSet<>()));
                    return found;
                })
                .collect(Collectors.toList());

        expenseRepository.delete(expenses);
    }

    @Override
    public void remove(int expenseId, int userId) throws ExpenseException {
        Optional<Expense> expenseById = expenseRepository.findOneByIdAndUserId(expenseId, userId);
        expenseById.orElseThrow(() -> new ExpenseException("Expense doesn't exist"));

        expenseRepository.delete(expenseId);
    }

    private ExpensesQueryResponseDTO mapToExpensesQueryResponseDTO(PageRequest pageRequest, List<ExpenseDTO> allExpensesOf, int totalSize) {
        List<GroupedExpensesDTO> groupedExpensesDTOs = groupBySpentDateAndCollect(allExpensesOf);

        return new ExpensesQueryResponseDTOBuilder()
                .withGroupedExpensesDTOList(groupedExpensesDTOs)
                .withCurrentPage(pageRequest.getPageNumber())
                .withCurrentSize(allExpensesOf.size())
                .withTotalSize(totalSize)
                .build();
    }

    private List<ExpenseDTO> mapToDTO(List<Expense> expenses) {
        return expenses
                .stream()
                .map(category -> dozerBeanMapper.map(category, ExpenseDTO.class))
                .collect(Collectors.toList());
    }
}