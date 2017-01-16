package com.leyuan.coach.widget.popupwindow;

import android.app.Activity;
import android.view.View;

import com.leyuan.coach.page.mvp.presenter.ClassNotifyPresenter;
import com.leyuan.coach.page.mvp.presenter.SuspendCoursePresenter;

/**
 * Created by user on 2016/12/27.
 */

public class PopupWindowSuspendCourseNotify extends PopupWindowClassNotify {

    public PopupWindowSuspendCourseNotify(Activity context) {
        super(context);
    }

    @Override
    protected ClassNotifyPresenter createPresenter(PopupWindowClassNotify popupWindowClassNotify, Activity context) {
        return new SuspendCoursePresenter(popupWindowClassNotify, context);
    }

    @Override
    protected void setNotifyLayoutVisible() {
        layoutTakeOverClass.setVisibility(View.GONE);
        btKnowSuspend.setVisibility(View.VISIBLE);
    }
}
