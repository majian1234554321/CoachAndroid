package com.leyuan.coach.page.mvp.model;

import com.leyuan.coach.bean.VersionInformation;
import com.leyuan.coach.http.RetrofitHelper;
import com.leyuan.coach.http.RxHelper;
import com.leyuan.coach.http.api.VersionService;

import rx.Subscriber;

/**
 * Created by user on 2017/1/4.
 */

public class VersionModel {

    VersionService service;

    public VersionModel() {
        service = RetrofitHelper.createApi(VersionService.class);
    }

    public void getVersionInfo(Subscriber<VersionInformation> subscriber) {
        service.getVersionInfo()
                .compose(RxHelper.<VersionInformation>transform())
                .subscribe(subscriber);
    }

}
