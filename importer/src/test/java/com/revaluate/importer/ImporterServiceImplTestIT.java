package com.revaluate.importer;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.importer.profile.MintExpenseProfile;
import com.revaluate.importer.profile.SpendeeExpenseProfile;
import com.univocity.parsers.common.processor.ObjectRowListProcessor;
import com.univocity.parsers.conversions.Conversions;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__importer__test.xml")
@ActiveProfiles("IT")
public class ImporterServiceImplTestIT {

    @Autowired
    private ImporterService importerService;

    @Test
    public void testDoParseAbc() throws Exception {
        CsvParserSettings settings = new CsvParserSettings();
        //the file used in the example uses '\n' as the line separator sequence.
        //the line separator sequence is defined here to ensure systems such as MacOS and Windows
        //are able to process this file correctly (MacOS uses '\r'; and Windows uses '\r\n').
        settings.getFormat().setLineSeparator("\n");

        // creates a CSV parser
        CsvParser parser = new CsvParser(settings);

        // parses all rows in one go.
        List<String[]> allRows = parser.parseAll(getReader("/mint.csv"));

        allRows.stream().forEach(strings -> System.out.println(Arrays.deepToString(strings)));
    }


    @Test
    public void testDoParse() throws Exception {
        ObjectRowListProcessor rowProcessor = new ObjectRowListProcessor();

        rowProcessor.convertFields(Conversions.toBigDecimal()).set("Amount");

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator("\n");
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);

        parserSettings.selectFields("Amount", "Description");

        CsvParser parser = new CsvParser(parserSettings);

        //the rowProcessor will be executed here.
        parser.parse(getReader("/mint.csv"));

        List<Object[]> rows = rowProcessor.getRows();
        System.out.println(rows);
    }

    @Test
    public void importFromMint() throws Exception {
        List<ExpenseDTO> expenseDTOs = importerService.importFrom(getReader("/mint.csv"), new MintExpenseProfile());

        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(2)));
    }

    @Test
    public void importFromSpendee() throws Exception {
        List<ExpenseDTO> expenseDTOs = importerService.importFrom(getReader("/spendee.csv"), new SpendeeExpenseProfile());

        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(6)));
    }

    public Reader getReader(String relativePath) throws UnsupportedEncodingException {
        return new InputStreamReader(this.getClass().getResourceAsStream(relativePath), "UTF-8");
    }
}