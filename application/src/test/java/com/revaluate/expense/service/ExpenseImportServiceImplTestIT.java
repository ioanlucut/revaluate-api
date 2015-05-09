package com.revaluate.expense.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.MintExpenseProfileDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ExpenseImportServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseImportService expenseImportService;

    @Autowired
    private ExpenseService expenseService;

    @Test
    public void importExpenses_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        String exampleString = "\"Date\",\"Description\",\"Original Description\",\"Amount\",\"Transaction Type\",\"Category\",\"Account Name\",\"Labels\",\"Notes\"\n" +
                "\"5/05/2015\",\"Sticky\",\"PaymentTo Sticky9\",\"36.98\",\"debit\",\"Home Insurance\",\"PayPal Account\",\"\",\"\"\n" +
                "\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"123.00\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"";

        InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
        List<ExpenseDTO> expenseDTOs = expenseImportService.importExpenses(inputStream, new MintExpenseProfileDTO(), createdUserDTO.getId());

        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesFor(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert imported expenses
        //-----------------------------------------------------------------
        assertThat(allExpensesFor, is(notNullValue()));
        assertThat(allExpensesFor.size(), is(equalTo(2)));

        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(2)));

        System.out.println(expenseDTOs);
        System.out.println(allExpensesFor);
    }
}