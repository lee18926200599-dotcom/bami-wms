package com.org.permission.server.utils;

import java.util.regex.Pattern;

public class StringRegular {

    private static String logisticsFuncCodeRegex="^[^\\r,^\\n,^\\s]{1,10}$";


    public static boolean logisticsFuncCodeValid(String logisticsFuncCode){
        try {
            return Pattern.matches(logisticsFuncCodeRegex, logisticsFuncCode);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
