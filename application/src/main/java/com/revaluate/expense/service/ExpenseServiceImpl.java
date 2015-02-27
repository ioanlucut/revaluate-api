package com.revaluate.expense.service;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.category.persistence.Category;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.expense.domain.ExpenseDTO;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
        if (expense.getCategory() == null) {
            throw new ExpenseException("Category is missing");
        }
        Optional<Category> categoryByName = categoryRepository.findOneByIdAndUserId(expense.getCategory().getId(), userId);
        Category categoryFound = categoryByName.orElseThrow(() -> new ExpenseException("Category is not proper"));

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

        //-----------------------------------------------------------------
        // Update the expense with given expense DTO
        //-----------------------------------------------------------------
        dozerBeanMapper.map(expenseDTO, foundExpense);

        Expense savedExpense = expenseRepository.save(foundExpense);
        return dozerBeanMapper.map(savedExpense, ExpenseDTO.class);
    }

    @Override
    public void remove(int expenseId, int userId) throws ExpenseException {
        Optional<Expense> expenseById = expenseRepository.findOneByIdAndUserId(expenseId, userId);
        expenseById.orElseThrow(() -> new ExpenseException("Expense doesn't exist"));

        expenseRepository.delete(expenseId);
    }
}