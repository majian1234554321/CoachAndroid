package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.CourseModel;
import com.leyuan.coach.page.mvp.view.NextMonthCourseViewListener;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/4.
 */

public class NextMonthCoursePresenter {

    CourseModel courseModel;
    NextMonthCourseViewListener viewListener;
    Context context;

    public NextMonthCoursePresenter(Context context, NextMonthCourseViewListener viewListener) {
        this.viewListener = viewListener;
        this.context = context;
        courseModel = new CourseModel();
    }

    public void getNextMonthUnconfirmCalendar() {

        courseModel.getNextMonthUnconfirmCalendar(new BaseSubscriber<ArrayList<MyCalendar>>(context) {
            @Override
            public void onNext(ArrayList<MyCalendar> myCalendars) {
                viewListener.onGetNextMonthUnconfirmCalendar(myCalendars);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onGetNextMonthUnconfirmCalendar(null);
            }
        });
    }

    public void getNextMonthConfirmedCalendar() {

        courseModel.getNextMonthConfrimedCalendar(new BaseSubscriber<ArrayList<MyCalendar>>(context) {
            @Override
            public void onNext(ArrayList<MyCalendar> myCalendars) {
                viewListener.onGetNextMonthconfirmedCalendar(myCalendars);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onGetNextMonthconfirmedCalendar(null);
            }
        });
    }

    public void getNextMonthUnconfirmCourseList(String courseTime) {
        courseModel.getNextMonthUnconfirmCourseList(new BaseSubscriber<CourseResult>(context) {
            @Override
            public void onNext(CourseResult courseNextMonthResult) {
                viewListener.onGetNextMonthCourseList(courseNextMonthResult);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onGetNextMonthCourseList(null);
            }
        }, courseTime);
    }

    public void getNextMonthConfirmedCourseList(String courseTime) {
        courseModel.getNextMonthConfirmedCourseList(new BaseSubscriber<CourseResult>(context) {
            @Override
            public void onNext(CourseResult courseNextMonthResult) {
                viewListener.onGetNextMonthCourseList(courseNextMonthResult);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onGetNextMonthCourseList(null);
            }
        }, courseTime);
    }

    public void confirmNextMonthCourse(String courseTime) {
        courseModel.nextMonthCourseConfirm(new BaseSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                viewListener.nextMonthCourseConfirm(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.nextMonthCourseConfirm(false);
            }
        }, courseTime);
    }


    public void refreshLoginStatus() {
        courseModel.refreshUserId();
    }
}
