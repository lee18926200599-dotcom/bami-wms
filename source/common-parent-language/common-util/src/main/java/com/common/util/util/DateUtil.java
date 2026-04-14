package com.common.util.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String DATE_TIME_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_TIME_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_TIME_FORMAT_YYMMDD = "yyMMdd";

    public static final String DATE_TIME_FORMAT_YYYY_MM = "yyyy-MM";

    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

    public static String parseStr(LocalDateTime localDateTime, String format){
        if (localDateTime == null){
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(localDateTime);
    }

    public static String localDateParseStr(LocalDate localDate, String format){
        if (localDate == null){
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(localDate);
    }

    public static LocalDate parseLocalDate(String localDate){
        if (!StringUtils.hasLength(localDate)) {
            return null;
        }
        if ("null".equals(localDate)) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_YYYY_MM_DD);
        LocalDate parse = LocalDate.parse(localDate, df);
        return parse;
    }
}
