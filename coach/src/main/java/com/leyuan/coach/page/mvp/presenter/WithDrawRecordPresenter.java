package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.WithdrawDetail;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.AccountBalanceModel;
import com.leyuan.coach.page.mvp.view.WithDrawRecordListener;
import com.leyuan.coach.page.mvp.view.WithDrawRecordMoreListener;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/29.
 */
public class WithDrawRecordPresenter {

    private Context context;
    private WithDrawRecordListener listener;
    private WithDrawRecordMoreListener listenerMore;

    private AccountBalanceModel model;

    public WithDrawRecordPresenter(Context context) {
        this.context = context;
        model = new AccountBalanceModel();
    }

    public void setWithDrawRecordListener(WithDrawRecordListener listener) {
        this.listener = listener;
    }

    public void setWithDrawRecordMoreListener(WithDrawRecordMoreListener listenerMore) {
        this.listenerMore = listenerMore;
    }

    public void getWithdrawRecord(final int page) {
        model.getWithdrawRecord(new BaseSubscriber<ArrayList<WithdrawDetail>>(context) {
            @Override
            public void onNext(ArrayList<WithdrawDetail> withdrawDetails) {
                if (listener != null)
                    listener.onGetWithDrawRecord(withdrawDetails, page);
            }
        }, String.valueOf(page));
    }

    public void getMoreWithdrawRecord(final int page) {
        model.getMoreWithdrawRecord(new BaseSubscriber<ArrayList<WithdrawDetail>>(context) {
            @Override
            public void onNext(ArrayList<WithdrawDetail> withdrawDetails) {

                if (listenerMore != null)
                    listenerMore.onGetMoreWithDrawRecord(withdrawDetails, page);
            }
        }, String.valueOf(page));
    }


}
