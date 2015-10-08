import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.parameter.Value;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.text.ParseException;
import java.util.TimeZone;

public class Test {

    public static final String ICAL_FORMAT = "FREQ=%s;BYDAY=%s;BYHOUR=%s;COUNT=%s";
    public static final int COUNTS = 5;
    public static final int HOUR = 8;

    public static void main(String[] args) throws ParseException {
        java.util.Date fromDate = LocalDateTime.now().toDateTime().withTimeAtStartOfDay().toDate();
        java.util.Date toDate = LocalDateTime.now().plusYears(1).toDateTime().withTimeAtStartOfDay().toDate();

        Recur aRecur = new Recur(String.format(ICAL_FORMAT, Recur.WEEKLY, WeekDay.MO, HOUR, COUNTS));
        DateList dates = aRecur.getDates(new Date(fromDate), new Date(toDate), Value.DATE_TIME);

        dates
                .stream()
                .forEach(date -> {
                    LocalDateTime localDateTime = LocalDateTime.fromDateFields(date);
                    System.out.println(localDateTime.toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone("ECT"))).compareTo(localDateTime.toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone("EST")))));;
                    System.out.println(localDateTime);
                    System.out.println(localDateTime.toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone("ECT"))));
                    System.out.println(localDateTime.toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone("EST"))));
                    System.out.println("------");
                });

        // Reminder: dueon
    }

}
