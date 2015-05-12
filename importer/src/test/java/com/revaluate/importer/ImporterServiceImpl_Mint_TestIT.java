package com.revaluate.importer;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.MintExpenseProfileDTO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__importer__test.xml")
@ActiveProfiles("IT")
public class ImporterServiceImpl_Mint_TestIT {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private ImporterParserService importerParserService;

    @Test
    public void importFromMint() throws Exception {
        List<ExpenseDTO> expenseDTOs = importerParserService.parseFrom(getReader("/mint.csv"), new MintExpenseProfileDTO());

        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(2)));
    }

    @Test
    public void importFromMintWithCategoriesInADifferentOrder() throws Exception {
        List<ExpenseDTO> expenseDTOs = importerParserService.parseFrom(getReader("/mint_cat_different_order.csv"), new MintExpenseProfileDTO());

        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(2)));
    }

    public Reader getReader(String relativePath) throws UnsupportedEncodingException {
        return new InputStreamReader(this.getClass().getResourceAsStream(relativePath), "UTF-8");
    }
}