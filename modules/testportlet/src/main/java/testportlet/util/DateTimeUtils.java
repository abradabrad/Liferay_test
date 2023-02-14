package testportlet.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    final static String PATTERN = "dd-MM-yyyy ã.";

    public static String dateToString (Date date) {
        return  DateTimeFormatter.ofPattern(PATTERN)
                .withLocale(new Locale("ru"))
                .format(date
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                );
    }

    public static String dateToString (Date date, FormatStyle formatStyle) {
        return  DateTimeFormatter
                .ofLocalizedDate(formatStyle)
                .withLocale(new Locale("ru"))
                .format(date
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                );
    }
}
