package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.CourseModel;
import com.leyuan.coach.page.mvp.view.CurrentCourseViewListener;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/23.
 */

public class CurrentCoursePresenter {

    CourseModel courseModel;
    CurrentCourseViewListener viewListener;
    Context context;

    public CurrentCoursePresenter(Context context, CurrentCourseViewListener viewListener) {
        this.context = context;
        this.viewListener = viewListener;
        courseModel = new CourseModel();
    }

    public void getCurrentMonthCalendar(){

        courseModel.getCurrentCalendar(new BaseSubscriber<ArrayList<MyCalendar>>(context) {
            @Override
            public void onNext(ArrayList<MyCalendar> myCalendars) {

                viewListener.onGetCalendar(myCalendars);

            }
        });
    }

    public void getCourseList(String courseTime){
        courseModel.getCourseList(new BaseSubscriber<CourseResult>(context) {
            @Override
            public void onNext(CourseResult courseResult) {

                viewListener.onGetCourseList(courseResult);
            }
        },courseTime);
    }

    public void signIn(String timetableId){
        courseModel.signIn(new BaseSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                viewListener.onSignResult(true);
            }
        },timetableId);
    }

    public void getReplaceCourseList(){
        courseModel.getReplaceCourseList(new BaseSubscriber<ArrayList<ClassSchedule>>(context) {
            @Override
            public void onNext(ArrayList<ClassSchedule> arrayList) {
                viewListener.onGetReplaceCourseListResult(arrayList);

            }
        });
    }

}
