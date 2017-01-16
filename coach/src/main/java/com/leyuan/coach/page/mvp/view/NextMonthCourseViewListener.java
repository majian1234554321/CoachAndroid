package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/4.
 */
public interface NextMonthCourseViewListener {
    void onGetNextMonthUnconfirmCalendar(ArrayList<MyCalendar> myCalendars);

    void onGetNextMonthCourseList(CourseResult courseNextMonthResult);

    void nextMonthCourseConfirm(boolean success);

    void onGetNextMonthconfirmedCalendar(ArrayList<MyCalendar> myCalendars);
}
