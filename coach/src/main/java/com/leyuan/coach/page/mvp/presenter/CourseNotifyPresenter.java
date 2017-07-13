package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.CourseModel;
import com.leyuan.coach.page.mvp.view.CourseNotifyViewListener;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/12.
 */
public class CourseNotifyPresenter {
    CourseModel courseModel;
    CourseNotifyViewListener viewListener;
    Context context;

    public CourseNotifyPresenter(CourseNotifyViewListener viewListener, Context context) {
        this.viewListener = viewListener;
        this.context = context;
        courseModel = new CourseModel();
    }

    public void getReplaceCourseList() {
        courseModel.getReplaceCourseList(new BaseSubscriber<ArrayList<ClassSchedule>>(context) {
            @Override
            public void onNext(ArrayList<ClassSchedule> arrayList) {
                viewListener.onGetReplaceCourseListResult(arrayList);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onGetReplaceCourseListResult(null);
            }
        });
    }

    public void getNewlyIncreaseCourseList() {
        courseModel.getNewlyIncreaseCourseList(new BaseSubscriber<ArrayList<ClassSchedule>>(context) {
            @Override
            public void onNext(ArrayList<ClassSchedule> arrayList) {
                viewListener.onGetNewlyIncreaseCourseListResult(arrayList);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onGetNewlyIncreaseCourseListResult(null);
            }
        });
    }

    public void getSuspendCourseList() {
        courseModel.getSuspendCourseList(new BaseSubscriber<ArrayList<ClassSchedule>>(context) {
            @Override
            public void onNext(ArrayList<ClassSchedule> arrayList) {
                viewListener.onGetSuspendCourseList(arrayList);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onGetSuspendCourseList(null);
            }
        });
    }
}
