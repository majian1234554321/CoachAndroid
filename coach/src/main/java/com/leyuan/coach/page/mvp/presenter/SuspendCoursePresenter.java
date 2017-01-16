package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.view.ClassNotifyViewListener;

/**
 * Created by user on 2016/12/27.
 */

public class SuspendCoursePresenter extends ClassNotifyPresenter {

    public SuspendCoursePresenter(ClassNotifyViewListener viewListener, Context context) {
        super(viewListener, context);
    }

    public void courseAgree(String timetableId, final int currentItem) {
        courseModel.confirmSuspendClass(new BaseSubscriber<Object>(context) {
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

    @Override
    public void courseRefuse(String timetableId, int currentItem) {

    }

}
