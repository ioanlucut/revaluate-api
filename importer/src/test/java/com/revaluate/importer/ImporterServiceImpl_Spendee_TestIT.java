package com.revaluate.importer;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.SpendeeExpenseProfileDTO;
import com.univocity.parsers.common.TextParsingException;
import org.junit.Ignore;
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
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__importer__test.xml")
@ActiveProfiles("IT")
public class ImporterServiceImpl_Spendee_TestIT {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private ImporterParserService importerParserService;

    @Test
    public void importFromSpendee_validCsv_isOk() throws Exception {
        List<ExpenseDTO> expenseDTOs = importerParserService.parseFrom(getReader("/spendee.csv"), new SpendeeExpenseProfileDTO());

        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(6)));
    }

    @Test
    public void importFromSpendee_validCsvLineSeparatorSpecified_isOk() throws Exception {
        List<ExpenseDTO> expenseDTOs = importerParserService.parseFrom(getReader("/spendee_big_working.csv"), new SpendeeExpenseProfileDTO());

        assertThat(expenseDTOs, is(notNullValue()));
    }

    @Test
    public void importFromSpendee_invalidCsvLineSeparatorSpecified_isOk() throws Exception {
        List<ExpenseDTO> expenseDTOs = importerParserService.parseFrom(getReader("/spendee_big_not_working.csv"), new SpendeeExpenseProfileDTO());

        assertThat(expenseDTOs, is(notNullValue()));
    }

    @Test
    public void importFromSpendee_oneCategoryMissing_exceptionThrown() throws ImporterException {
        Reader reader = new StringReader("\"CategoryX\";\"Localized CategoryX\";\"Date & Time\";\"Amount\";\"Notes\"\n" +
                "\"bills\";\"Bills\";\"2015-02-21T19:36:10GMT+02:00\";\"-22 RON\";\"\"");

        exception.expect(ImporterException.class);
        exception.expectMessage("The csv is not valid");
        exception.expectCause(any(TextParsingException.class));
        importerParserService.parseFrom(reader, new SpendeeExpenseProfileDTO());
    }

    @Test
    public void importFromSpendee_otherTypeUploadedLikeMint_exceptionThrown() throws ImporterException, UnsupportedEncodingException {
        exception.expect(ImporterException.class);
        exception.expectMessage("The csv is not valid");
        exception.expectCause(any(TextParsingException.class));
        importerParserService.parseFrom(getReader("/mint.csv"), new SpendeeExpenseProfileDTO());
    }

    @Test
    public void importFromSpendee_unexpectedInput_handledOk() throws ImporterException {
        Reader reader = new StringReader("\"Category\";\"Localized Category\";\"Date & Time\";\"Amount\";\"Notes\"\n" +
                "\"bills\";\"Bills\";\"2015-02-21T19:36:10+02:00\";\"-22 RON\";\"\"");

        exception.expect(ImporterException.class);
        exception.expectMessage("Invalid format:");
        importerParserService.parseFrom(reader, new SpendeeExpenseProfileDTO());

        reader = new StringReader("\"Category\";\"Localized Category\";\"Date & Time\";\"Amount\";\"Notes\"\n" +
                "\"bills\";\"\";\"2015-02-21T19:36:10GMT+02:00\";\"-22 RON\";\"\"");

        importerParserService.parseFrom(reader, new SpendeeExpenseProfileDTO());
    }

    @Test
    @Ignore
    public void import_categoryNotPresent_handledOk() throws ImporterException {
        Reader reader = new StringReader("\"Category\";\"Localized Category\";\"Date & Time\";\"Amount\";\"Notes\"\n" +
                "\"\";\"\";\"\";\"\";\"\"");

        List<ExpenseDTO> expenseDTOs = importerParserService.parseFrom(reader, new SpendeeExpenseProfileDTO());
        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(1)));
        assertThat(expenseDTOs.get(0).getValue(), is(equalTo(0.0)));
    }

    @Test
    public void import_amountNonDigit_handledOk() throws ImporterException {
        Reader reader = new StringReader("\"Category\";\"Localized Category\";\"Date & Time\";\"Amount\";\"Notes\"\n" +
                "\"bills\";\"Bills\";\"2015-02-21T19:36:10GMT+02:00\";\"-a!@#$%^&*()_+=|}{/;22 RON\";\"\"");

        exception.expect(ImporterException.class);
        importerParserService.parseFrom(reader, new SpendeeExpenseProfileDTO());
    }

    public Reader getReader(String relativePath) throws UnsupportedEncodingException {
        return new InputStreamReader(this.getClass().getResourceAsStream(relativePath), "UTF-8");
    }
}