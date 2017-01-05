package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.VersionInfomation;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.VersionModel;
import com.leyuan.coach.page.mvp.view.VersionViewListener;

/**
 * Created by user on 2017/1/4.
 */

public class VersionPresenter {

    private Context context;
    private VersionViewListener listener;
    private VersionModel model;

    public VersionPresenter(Context context, VersionViewListener listener) {
        this.context = context;
        this.listener = listener;
        model = new VersionModel();
    }

    public void getVersionInfo() {
        model.getVersionInfo(new BaseSubscriber<VersionInfomation>(context) {
            @Override
            public void onNext(VersionInfomation versionInfomation) {
                listener.onGetVersionInfo(versionInfomation);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listener.onGetVersionInfo(null);
            }
        });
    }
}
