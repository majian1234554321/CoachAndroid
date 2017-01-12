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
    private static SimpleDateFormat yearMonthFormat = new SimpleDateFormat(yearMonth);
    private static SimpleDateFormat yearMonthDayFormat = new SimpleDateFormat(yearMonthDay);

    public static String getCurrentDay() {
        return DateFormat.format(yearMonthDay, Calendar.getInstance()).toString();
    }


    public static String getCurrentMonth() {
        return DateFormat.format(yearMonth, Calendar.getInstance()).toString();
    }

    public static int getFirstWeekDayByMonth(String month) {
        return getFirstWeekDay(month, yearMonth);
    }


    public static MonthIndex compareMonth(String month) {

        try {
            Date inDate = yearMonthFormat.parse(month);
            Date date = new Date();

            if (inDate.getYear() < date.getYear()) {
                return MonthIndex.MONTH_PRE;
            } else if (inDate.getYear() == date.getYear() && inDate.getMonth() < date.getMonth()) {
                return MonthIndex.MONTH_PRE;
            } else if (inDate.getYear() == date.getYear() && inDate.getMonth() == date.getMonth()) {
                return MonthIndex.MONTH_CURRENT;
            } else {
                return MonthIndex.MONTH_NEXT;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return MonthIndex.MONTH_NEXT;
    }


    public static int getCurrentDay(String month) {
        SimpleDateFormat yearMonthFormat = new SimpleDateFormat(yearMonth);
        try {
            Date inDate = yearMonthFormat.parse(month);
            Date date = new Date();
            if (date.getYear() == inDate.getYear() && date.getMonth() == inDate.getMonth()) {
                LogUtil.i("date", "date.getDate , === currentDay :" + date.getDate());
                return date.getDate() - 1;

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

    public static String formatMonthDayByYearMonth(String timeMouth) {
        String day = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yearMonthDayFormat.parse(timeMouth));
            day = (calendar.get(Calendar.MONTH)) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static String formatWeekByYearMonth(String timeMouth) {
        String day = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yearMonthDayFormat.parse(timeMouth));
            day = formatWeek(calendar.get(Calendar.DAY_OF_WEEK));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    private static String formatWeek(int dayOfWeek) {
        String week = "";
        switch (dayOfWeek) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }

        return week;
    }

    /**
     * @param timeMouth      2016-11
     * @param selectPosition index from 0
     * @return
     */
    public static String formatYearMonthDay(String timeMouth, int selectPosition) {
        return timeMouth + "-" + NumberUtils.intToString(selectPosition + 1);
    }

    public static int getCurrentPositionByMonth(String timeMouth) {

        Calendar currentCalendar = Calendar.getInstance();
        Calendar inCalendar = Calendar.getInstance();

        try {
            inCalendar.setTime(yearMonthFormat.parse(timeMouth));

            if (currentCalendar.get(Calendar.YEAR) == inCalendar.get(Calendar.YEAR) &&
                    currentCalendar.get(Calendar.MONTH) == inCalendar.get(Calendar.MONTH)) {
                return currentCalendar.get(Calendar.DAY_OF_MONTH) - 1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public enum MonthIndex {
        MONTH_PRE, MONTH_CURRENT, MONTH_NEXT;
    }

}
