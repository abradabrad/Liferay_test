package testportlet.util;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateTimeUtils {


    public static String dateToString (Date date, String pattern) {
        if (Objects.isNull(date)) {
            return "";
        }
        return  DateTimeFormatter.ofPattern(pattern)
                .withLocale(new Locale("ru"))
                .format(date
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                );
    }
}
