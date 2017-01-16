package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.page.mvp.model.CourseModel;
import com.leyuan.coach.page.mvp.view.ClassNotifyViewListener;

/**
 * Created by user on 2017/1/13.
 */
public abstract class ClassNotifyPresenter {

    CourseModel courseModel;
    ClassNotifyViewListener viewListener;
    Context context;

    public ClassNotifyPresenter(ClassNotifyViewListener viewListener, Context context) {
        this.viewListener = viewListener;
        this.context = context;
        courseModel = new CourseModel();
    }

    public abstract void courseAgree(String timetableId, final int currentItem);

    public abstract void courseRefuse(String timetableId, final int currentItem);
}
