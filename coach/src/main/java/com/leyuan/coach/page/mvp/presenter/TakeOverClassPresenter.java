package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.view.ClassNotifyViewListener;

/**
 * Created by user on 2016/12/27.
 */

public class TakeOverClassPresenter extends ClassNotifyPresenter {

    public TakeOverClassPresenter(ClassNotifyViewListener viewListener, Context context) {
        super(viewListener, context);
    }

//    @Deprecated
//    public void getReplaceCourseList() {
//        courseModel.getReplaceCourseList(new BaseSubscriber<ArrayList<ClassSchedule>>(context) {
//            @Override
//            public void onNext(ArrayList<ClassSchedule> arrayList) {
////                viewListener.onGetRepalceCourseList(arrayList);
//            }
//        });
//    }

    public void courseAgree(String timetableId, final int currentItem) {
        courseModel.takeOverClassAgree(new BaseSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                viewListener.onAgreeResult(true, currentItem);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onAgreeResult(false, currentItem);
            }
        }, timetableId);

    }

    public void courseRefuse(String timetableId, final int currentItem) {
        courseModel.takeOverClassRefuse(new BaseSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                viewListener.onRefuseResult(true, currentItem);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onRefuseResult(false, currentItem);

            }
        }, timetableId);
    }


}
