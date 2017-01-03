package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.CourseModel;
import com.leyuan.coach.page.mvp.view.TakeOverClassViewListener;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/27.
 */

public class TakeOverClassPresenter {

    CourseModel courseModel;
    TakeOverClassViewListener viewListener;
    Context context;

    public TakeOverClassPresenter(TakeOverClassViewListener viewListener, Context context) {
        this.viewListener = viewListener;
        this.context = context;
    }

    public void getReplaceCourseList(){
        courseModel.getReplaceCourseList(new BaseSubscriber<ArrayList<ClassSchedule>>(context) {
            @Override
            public void onNext(ArrayList<ClassSchedule> arrayList) {
                viewListener.onGetRepalceCourseList( arrayList);
            }
        });
    }

    public void takeOverClassAgree( String timetableId) {
        courseModel.takeOverClassAgree(new BaseSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                viewListener.onAgreeResult(true);
            }
        },timetableId);

    }

    public void takeOverClassRefuse( String timetableId) {
        courseModel.takeOverClassRefuse(new BaseSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                viewListener.onRefuseResult(true);
            }
        },timetableId);
    }



}
