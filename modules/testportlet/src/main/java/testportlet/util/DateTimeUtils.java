package testportlet.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateTimeUtils {


    public static String dateToString(Date date, String pattern) {
        if (Objects.isNull(date)) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }
}
