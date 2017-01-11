package com.leyuan.coach.utils;

import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.commonlibrary.util.MyDateUtils;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/9.
 */
public class CourseDateUtils {

    private static String getCurrentDataByPositin(int currentCalendarPosition, ArrayList<MyCalendar> myCalendars) {
        String time = "";
        int temp = 0, monthClickedIndex = 0;
        for (; monthClickedIndex < myCalendars.size(); monthClickedIndex++) {
            temp += myCalendars.get(monthClickedIndex).getDayList().length;
            if (currentCalendarPosition < temp) {
                time = MyDateUtils.formatYearMonthDay(myCalendars.get(monthClickedIndex).getTimeMouth(),
                        currentCalendarPosition - temp + myCalendars.get(monthClickedIndex).getDayList().length);
                break;
            }
        }
        return time;
    }
}
