package com.example.ngenge.journal.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getDate(int year,int month,int day)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);

        return calendar.getTime();
    }
}
