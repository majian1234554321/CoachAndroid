package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.RecentEarningResult;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.AccountBalanceModel;
import com.leyuan.coach.page.mvp.view.AccoutBalanceViewListener;
import com.leyuan.coach.page.mvp.view.RecentEarningViewListener;

/**
 * Created by user on 2017/1/6.
 */

public class AccountBalancePresenter {
    private Context context;
    private AccoutBalanceViewListener listener;
    private RecentEarningViewListener recentEarningListener;
    private AccountBalanceModel model;

    public AccountBalancePresenter(Context context) {
        this.context = context;
        model = new AccountBalanceModel();

    }

    public void setAccoutBalanceViewListener(AccoutBalanceViewListener listener) {
        this.listener = listener;
    }

    public void setRecentEarningViewListener(RecentEarningViewListener recentEarningListener) {
        this.recentEarningListener = recentEarningListener;
    }


    public void getBalance() {
        model.getBalance(new BaseSubscriber<String>(context) {
            @Override
            public void onNext(String s) {
                if (listener != null)
                    listener.onGetBalance(s);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (listener != null)
                    listener.onGetBalance(null);
            }
        });
    }

    public void getRecentEarning(String cashTime) {
        model.getRecentEarning(new BaseSubscriber<RecentEarningResult>(context) {
            @Override
            public void onNext(RecentEarningResult recentEaringResult) {
                if (recentEarningListener != null) {
                    recentEarningListener.onGetRecentEarning(recentEaringResult);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (recentEarningListener != null) {
                    recentEarningListener.onGetRecentEarning(null);
                }

            }
        }, cashTime);
    }


}
