package com.leyuan.coach.page.mvp.view;

import android.os.Bundle;

import com.leyuan.coach.bean.ClassSchedule;

/**
 * Created by user on 2017/1/9.
 */
public interface ClassScheduleViewListener {
    void onRightButtonClick(int id);

    void onItemClick(ClassSchedule course);

    void startActivityForResult(int currentCalendarPosition, Bundle bundle);

    void requestCourseData(String currentData, int currentCalendarPosition);

}
