package com.leyuan.coach.page.mvp.model;

import com.leyuan.coach.bean.WithdrawDetail;
import com.leyuan.coach.http.RetrofitHelper;
import com.leyuan.coach.http.RxHelper;
import com.leyuan.coach.http.api.AccountBalanceService;
import com.leyuan.coach.page.App;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by user on 2016/12/28.
 */

public class AccountBalanceModel {

    private AccountBalanceService service;
    private String id;

    public AccountBalanceModel() {
        service = RetrofitHelper.createApi(AccountBalanceService.class);
        if (App.getInstance().isLogin()) {
            id = String.valueOf(App.getInstance().getUser().getId());
        }
    }

    public void getBalance(Subscriber<String> subscriber){
        getBalance(subscriber,id);
    }

    public void getBalance(Subscriber<String> subscriber, String coachId){
        service.getBalance(coachId)
                .compose(RxHelper.<String>transform())
                .subscribe(subscriber);
    }

    public void getWithdrawRecord(Subscriber<ArrayList<WithdrawDetail>> subscriber,String page){
        service.getWithdrawRecord(id,page)
                .compose(RxHelper.<ArrayList<WithdrawDetail>>transform())
                .subscribe(subscriber);
    }

    public void getMoreWithdrawRecord(Subscriber<ArrayList<WithdrawDetail>> subscriber,String page){
        service.getMoreWithdrawRecord(id,page)
                .compose(RxHelper.<ArrayList<WithdrawDetail>>transform())
                .subscribe(subscriber);
    }

    public void withdraw(Subscriber<Object> subscriber,String aliId,String userName,String money){
        service.withdraw(id,aliId,userName,money)
                .compose(RxHelper.transform())
                .subscribe(subscriber);
    }






}
