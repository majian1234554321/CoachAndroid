package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/23.
 */

public interface CurrentCourseViewListener {

    void onGetCalendar(ArrayList<MyCalendar> myCalendars);

    void onGetCourseList(CourseResult courseResult);

    void onSignResult(boolean b);

    void onGetReplaceCourseListResult(ArrayList<ClassSchedule> arrayList);
}
