package com.leyuan.coach.page.mvp.model;

import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.http.RetrofitHelper;
import com.leyuan.coach.http.RxHelper;
import com.leyuan.coach.http.api.SignService;
import com.leyuan.coach.page.App;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by user on 2017/1/3.
 */

public class SignModel {

    private String id;
    private SignService service;

    public SignModel() {
        service = RetrofitHelper.createApi(SignService.class);
        if (App.getInstance().isLogin()) {
            id = String.valueOf(App.getInstance().getUser().getId());
        }
    }


    public void getSignInList(Subscriber<ArrayList<ClassSchedule>> subscriber, String signTime, String page) {
        service.getSignInList(id, signTime,page)
                .compose(RxHelper.<ArrayList<ClassSchedule>>transform())
                .subscribe(subscriber);
    }

}
