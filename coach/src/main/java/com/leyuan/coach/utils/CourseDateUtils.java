package com.leyuan.coach.utils;

import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.commonlibrary.util.MyDateUtils;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/9.
 */
public class CourseDateUtils {

    public static String getCalendarDateByPosition(int currentCalendarPosition, ArrayList<MyCalendar> myCalendars) {
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
        LogUtil.i("course", "getCalendarDateByPosition time = " + time);

        return time;
    }

    public static String getCurrentMonthByPosition(int position, ArrayList<MyCalendar> myCalendars) {
        String month = "";
        int temp = 0;
        for (int monthIndex = 0; monthIndex < myCalendars.size(); monthIndex++) {
            temp += myCalendars.get(monthIndex).getDayList().length;
            if (position < temp) {
                month = myCalendars.get(monthIndex).getTimeMouth();
                LogUtil.i("position", " getCurrentMonthByPosition = " + month);
                break;
            }
        }

        return month;
    }

    public static String getCurrentMonthByPositionQuickly(int position, ArrayList<MyCalendar> myCalendars) {
        if (position < myCalendars.get(0).getDayList().length) {
            return myCalendars.get(0).getTimeMouth();
        } else if (position < myCalendars.get(0).getDayList().length + myCalendars.get(1).getDayList().length) {
            return myCalendars.get(1).getTimeMouth();
        } else {
            return myCalendars.get(2).getTimeMouth();
        }
    }

    public static int getFirstHaveCoursePosition(ArrayList<MyCalendar> myCalendars) {
        int position = 0, temp = 0;
        for (MyCalendar myCalendar : myCalendars) {
            for (Integer num : myCalendar.getDayList()) {
                if (num > 0) {
                    return position;
                } else {
                    position++;
                }
            }
        }
        LogUtil.i("course", "getFirstHaveCoursePosition = " + position);
        return temp;
    }
}
