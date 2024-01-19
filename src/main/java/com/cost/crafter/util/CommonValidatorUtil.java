package com.cost.crafter.util;

import java.util.regex.Pattern;

public class CommonValidatorUtil {

    public static boolean isEmailValid(String email) {
        final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

    public static boolean isAlpha(String text) {
        return text.chars().allMatch(Character::isLetter);
    }

    public static boolean isBudgetMonthValid(String month) {
        final String regexPattern = "^\\d{4}-\\d{2}$";
        return Pattern.compile(regexPattern).matcher(month).matches();
    }
}
