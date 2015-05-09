package com.revaluate.importer;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.MintExpenseProfileDTO;
import com.revaluate.domain.importer.profile.SpendeeExpenseProfileDTO;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
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
public class ImporterServiceImplTestIT {

    @Autowired
    private ImporterService importerService;

    @Test
    public void importFromMint() throws Exception {
        List<ExpenseDTO> expenseDTOs = importerService.importFrom(getReader("/mint.csv"), new MintExpenseProfileDTO());

        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(2)));
    }

    @Test
    public void importFromSpendee() throws Exception {
        List<ExpenseDTO> expenseDTOs = importerService.importFrom(getReader("/spendee.csv"), new SpendeeExpenseProfileDTO());

        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(6)));
    }

    @Test
    public void justPlaying() throws Exception {
        String x = "2015-02-21T19:36:10GMT+02:00";
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'GMT'Z");

        LocalDateTime s = df.withOffsetParsed()
                .parseLocalDateTime(x);
        System.out.println(s);

        String xx = "5/04/2015";
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/YYY");
        LocalDateTime localDateTime = dateTimeFormatter.withOffsetParsed()
                .parseLocalDateTime(xx);

        System.out.println(localDateTime);

        try {
            ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime(xx);
        } catch (IllegalArgumentException ex) {

            ISODateTimeFormat.dateTime().parseLocalDateTime(xx);
        }
    }

    public Reader getReader(String relativePath) throws UnsupportedEncodingException {
        return new InputStreamReader(this.getClass().getResourceAsStream(relativePath), "UTF-8");
    }
}