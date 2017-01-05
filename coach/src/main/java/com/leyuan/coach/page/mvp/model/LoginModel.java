package com.leyuan.coach.page.mvp.model;

import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.http.RetrofitHelper;
import com.leyuan.coach.http.RxHelper;
import com.leyuan.coach.http.api.LoginService;
import com.leyuan.coach.page.App;

import rx.Subscriber;

/**
 * Created by user on 2016/12/19.
 */

public class LoginModel {


    private LoginService mLoginService;

    public LoginModel() {
        mLoginService = RetrofitHelper.createApi(LoginService.class);
    }

    public void login(Subscriber<UserCoach> subscriber, String account, String password) {
        mLoginService.login(account, password, App.getInstance().getVersionName())
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);
    }

    public void getImageIdentify(Subscriber<String> subscriber, String phone) {
        mLoginService.getImageIdentify(phone)
                .compose(RxHelper.<String>transform())
                .subscribe(subscriber);
    }

    public void getIdentify(Subscriber<String> subscriber, String phone, String code) {
        mLoginService.getIdentify(phone, code)
                .compose(RxHelper.<String>transform())
                .subscribe(subscriber);
    }

    public void autoLogin(Subscriber<UserCoach> subscriber) {
        mLoginService.autoLogin()
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);
    }

    public void loginOut(Subscriber<String> subscriber) {
        mLoginService.loginOut()
                .compose(RxHelper.<String>transform())
                .subscribe(subscriber);
    }

}
