package com.basedata.common.util;


public class BusiNumberUtil {

    public static String generateIdentifier(char firstLetter, int length) {
        StringBuilder identifier = new StringBuilder();
        identifier.append(firstLetter);

        int remainingLength = length - 1;
        for (int i = 0; i < remainingLength; i++) {
            int randomNumber = (int) (Math.random() * 10);
            identifier.append(randomNumber);
        }

        return identifier.toString();
    }
}
