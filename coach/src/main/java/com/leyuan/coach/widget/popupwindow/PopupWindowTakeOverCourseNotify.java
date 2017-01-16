package com.leyuan.coach.widget.popupwindow;

import android.app.Activity;
import android.view.View;

import com.leyuan.coach.page.mvp.presenter.ClassNotifyPresenter;
import com.leyuan.coach.page.mvp.presenter.TakeOverClassPresenter;

/**
 * Created by user on 2016/12/27.
 */

public class PopupWindowTakeOverCourseNotify extends PopupWindowClassNotify {

    public PopupWindowTakeOverCourseNotify(Activity context) {
        super(context);
    }

    @Override
    protected ClassNotifyPresenter createPresenter(PopupWindowClassNotify popupWindowClassNotify, Activity context) {
        return new TakeOverClassPresenter(popupWindowClassNotify, context);
    }

    @Override
    protected void setNotifyLayoutVisible() {
        layoutTakeOverClass.setVisibility(View.VISIBLE);
        btKnowSuspend.setVisibility(View.GONE);
    }
}
