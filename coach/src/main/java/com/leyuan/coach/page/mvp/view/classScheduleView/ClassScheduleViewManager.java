package com.leyuan.coach.page.mvp.view.classScheduleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.page.mvp.view.ClassScheduleViewListener;
import com.leyuan.coach.utils.CourseDateUtils;
import com.leyuan.commonlibrary.util.MyDateUtils;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/11.
 */

public class ClassScheduleViewManager {

    private ClassScheduleViewListener listener;
    BaseClassScheduleView scheduleView;
    private Context context;
    private ScheduleMode scheduleMode;

    private ClassScheduleViewManager(Context context, ScheduleMode scheduleMode) {
        this(context, scheduleMode, null);

    }

    public ClassScheduleViewManager(Context context, ScheduleMode scheduleMode, ClassScheduleViewListener listener) {
        this.context = context;
        this.listener = listener;
        if (scheduleMode == ScheduleMode.CURRENT_MONTH) {
            scheduleView = new CurrentMonthClassScheduleView(context);
        } else {
            scheduleView = new NextMonthClassScheduleView(context);
        }

    }

    public void onCreateView(ViewGroup container) {
        scheduleView.createView(LayoutInflater.from(context), container, true);
    }

    public void onViewCreated() {
        scheduleView.initViewData();
    }

    public void onGetCalendarData(ArrayList<MyCalendar> myCalendars, int calendarPosition) {

        scheduleView.setCalendarData(myCalendars, calendarPosition);
    }

    public void onGetCalendarData(ArrayList<MyCalendar> myCalendars) {
        int position = 0;
        if (scheduleMode == ScheduleMode.CURRENT_MONTH) {
            for (MyCalendar calendar : myCalendars) {
                if (MyDateUtils.getCurrentPositionByMonth(calendar.getTimeMouth()) >= 0) {
                    position += MyDateUtils.getCurrentPositionByMonth(calendar.getTimeMouth());
                    break;
                } else {
                    position += calendar.getDayList().length;
                }
            }
        } else {
            position = CourseDateUtils.getFirstHaveCoursePosition(myCalendars);
        }
        onGetCalendarData(myCalendars, position);
    }


    public void onGetCourseListData(CourseResult courseResult) {
        scheduleView.setCourseList(courseResult);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        scheduleView.setActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {
        scheduleView.destroyView();
    }

    public enum ScheduleMode {
        CURRENT_MONTH, NEXT_MONTH;
    }

}
