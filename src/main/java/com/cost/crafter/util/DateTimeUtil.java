package com.cost.crafter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtil {

    public static boolean isValid(String strDate, String format) {
        try
        {
            SimpleDateFormat sdfrmt = new SimpleDateFormat(format);
            sdfrmt.setLenient(false);
            sdfrmt.parse(strDate);
            return true;
        }
        catch (ParseException e)
        {
            return false;
        }
    }
}
