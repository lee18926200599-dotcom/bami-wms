package com.common.util.util;


public class NumberUtil {
    public static Integer safeParseInt(String str) {
        return safeParseInt(str, null);
    }

    public static Integer safeParseInt(String str, Integer defaultValue) {
        if (str == null || str.trim().isEmpty()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
