package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.page.mvp.model.CourseModel;
import com.leyuan.coach.page.mvp.view.NextMonthCourseViewListener;

/**
 * Created by user on 2017/1/4.
 */

public class NextMonthCoursePresenter {

    CourseModel courseModel;
    NextMonthCourseViewListener viewListener;
    Context context;

    public NextMonthCoursePresenter(CourseModel courseModel, NextMonthCourseViewListener viewListener) {
        this.courseModel = courseModel;
        this.viewListener = viewListener;
    }


}
