package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.AccountBalanceModel;
import com.leyuan.coach.page.mvp.view.WithDrawViewListener;

/**
 * Created by user on 2016/12/29.
 */
public class WithDrawPresenter {
    private Context context;
    private WithDrawViewListener listener;
    private AccountBalanceModel model;

    public WithDrawPresenter(Context context, WithDrawViewListener listener) {
        this.context = context;
        this.listener = listener;
        model = new AccountBalanceModel();
    }

    public void withdraw(String aliId, String userName, String money) {
        model.withdraw(new BaseSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                listener.onWithDrawResult(true);

            }
        }, aliId, userName, money);
    }

}
