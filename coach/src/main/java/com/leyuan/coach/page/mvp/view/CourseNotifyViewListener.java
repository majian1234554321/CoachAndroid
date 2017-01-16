package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.ClassSchedule;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/12.
 */
public interface CourseNotifyViewListener {
    void onGetReplaceCourseListResult(ArrayList<ClassSchedule> arrayList);

    void onGetSuspendCourseList(ArrayList<ClassSchedule> arrayList);
}
