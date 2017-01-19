package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.SignModel;
import com.leyuan.coach.page.mvp.view.SignViewListener;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/4.
 */

public class SignPresenter {

    private Context context;
    private SignViewListener listener;
    private SignModel model;

    public SignPresenter(Context context, SignViewListener listener) {
        this.context = context;
        this.listener = listener;
        model = new SignModel();
    }

    public void getSignInList(String signTime, final int page) {
        model.getSignInList(new BaseSubscriber<ArrayList<ClassSchedule>>(context) {
            @Override
            public void onNext(ArrayList<ClassSchedule> arrayList) {
                listener.onGetSignList(arrayList,page);
            }
        }, signTime, String.valueOf(page));
    }

    public void getMonthList() {
        model.getMonthList(new BaseSubscriber<ArrayList<String>>(context) {
            @Override
            public void onNext(ArrayList<String> strings) {
                 listener.onGetMonthList(strings);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

}
