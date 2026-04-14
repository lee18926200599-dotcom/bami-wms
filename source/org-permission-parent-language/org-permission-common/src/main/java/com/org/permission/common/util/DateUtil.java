package com.org.permission.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String YYYY = "yyyy";
    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String parseDateToStr(final String format, final Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static final boolean validateTimeBetween(final Date startDate, final Date endDate) {
        try {
            Date now = new Date();
            boolean result = true;
            if (startDate != null) {
                result = now.after(startDate);
            }
            if (endDate != null) {
                result = result && now.before(endDate);
            }
            return result;
        } catch (Exception e) {
            return false;
        }
    }
}
