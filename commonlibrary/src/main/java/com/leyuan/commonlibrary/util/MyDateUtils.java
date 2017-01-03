package com.leyuan.commonlibrary.util;

import android.text.format.DateFormat;

import com.facebook.stetho.common.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 2016/12/26.
 */

public class MyDateUtils {
    private static String yearMonthDay = "yyyy-MM-dd";
    private static String yearMonth = "yyyy-MM";

    public static String getCurrentDay() {
        return DateFormat.format(yearMonthDay, Calendar.getInstance()).toString();
    }

    public static int getFirstWeekDayByMonth(String month) {
        return getFirstWeekDay(month, yearMonth);
    }

    public static int getCurrentDay(String month) {
        SimpleDateFormat yearMonthFormat = new SimpleDateFormat(yearMonth);
        try {
            Date inDate = yearMonthFormat.parse(month);
            Date date = new Date();
            if (date.getYear() == inDate.getYear() || date.getMonth() == inDate.getMonth()) {
                LogUtil.i("date", "date.getDate , === currentDay :" + date.getDate());
                return date.getDate();

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return -1;

    }

    /**
     * @return sunday-0,monday-1,tuesday-2,...,saturday-6
     */
    public static int getFirstWeekDay(String month, String format) {
        SimpleDateFormat yearMonthFormat = new SimpleDateFormat(format);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yearMonthFormat.parse(month));
            return calendar.get(Calendar.DAY_OF_WEEK) - 1;

        } catch (ParseException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
