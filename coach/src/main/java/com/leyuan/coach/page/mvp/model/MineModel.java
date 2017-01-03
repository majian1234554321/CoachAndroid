package com.leyuan.coach.page.mvp.model;

import com.leyuan.coach.bean.CoachInfo;
import com.leyuan.coach.http.RetrofitHelper;
import com.leyuan.coach.http.RxHelper;
import com.leyuan.coach.http.api.MineService;

import rx.Subscriber;

/**
 * Created by user on 2016/12/19.
 */

public class MineModel {


    private MineService mineService;

    public MineModel() {
        mineService = RetrofitHelper.createApi(MineService.class);
    }

    public void getUserInfo(Subscriber<CoachInfo> subscriber, String id) {
        mineService.getUserInfo(id)
                .compose(RxHelper.<CoachInfo>transform())
                .subscribe(subscriber);
    }


}
