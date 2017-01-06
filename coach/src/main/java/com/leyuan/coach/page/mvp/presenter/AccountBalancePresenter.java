package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.AccountBalanceModel;
import com.leyuan.coach.page.mvp.view.AccoutBalanceViewListener;

/**
 * Created by user on 2017/1/6.
 */

public class AccountBalancePresenter {
    private Context context;
    private AccoutBalanceViewListener listener;
    private AccountBalanceModel model;

    public AccountBalancePresenter(Context context) {
        this.context = context;
        model = new AccountBalanceModel();

    }

    public void setAccoutBalanceViewListener(AccoutBalanceViewListener listener){
        this.listener = listener;
    }

    public void getBalance(){
        model.getBalance(new BaseSubscriber<String>(context) {
            @Override
            public void onNext(String s) {
                if(listener!= null)
                    listener.onGetBalance(s);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(listener!= null)
                    listener.onGetBalance(null);
            }
        });
    }


}
