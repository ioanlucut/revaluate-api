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
import java.io.StringReader;
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

    @Test
    public void importFromMintWithProperPrice() throws Exception {
        Reader reader = new StringReader("\"Amount\",\"Date\",\"Description\",\"Original Description\",\"Transaction Type\",\"Category\",\"Account Name\",\"Labels\",\"Notes\"\n" +
                "\"36.98\",\"5/05/2015\",\"Sticky\",\"PaymentTo Sticky9\",\"debit\",\"Home Insurance\",\"PayPal Account\",\"\",\"\"\n" +
                "\"1111.98\",\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"\n" +
                "\"10000.98\",\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"\n" +
                "\"23.4\",\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"\n" +
                "\"23\",\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"\n" +
                "\"23.23232\",\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"\n" +
                "\"23.23.23\",\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"\n" +
                "\"123131231\",\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"");

        List<ExpenseDTO> expenseDTOs = importerParserService.parseFrom(reader, new MintExpenseProfileDTO());


        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(8)));

        assertThat(expenseDTOs.get(0).getValue(), is(equalTo(36.98)));
        assertThat(expenseDTOs.get(1).getValue(), is(equalTo(1111.98)));
        assertThat(expenseDTOs.get(2).getValue(), is(equalTo(10000.98)));
        assertThat(expenseDTOs.get(3).getValue(), is(equalTo(23.4)));
        assertThat(expenseDTOs.get(4).getValue(), is(equalTo(23.00)));
        assertThat(expenseDTOs.get(5).getValue(), is(equalTo(23.23)));
        assertThat(expenseDTOs.get(6).getValue(), is(equalTo(23.23)));
        assertThat(expenseDTOs.get(7).getValue(), is(equalTo(123131231.00)));
    }

    public Reader getReader(String relativePath) throws UnsupportedEncodingException {
        return new InputStreamReader(this.getClass().getResourceAsStream(relativePath), "UTF-8");
    }


}