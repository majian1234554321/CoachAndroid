package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.mvp.model.LoginModel;
import com.leyuan.coach.page.mvp.view.LoginViewListener;

/**
 * Created by user on 2016/12/19.
 */

public class LoginPresenter {
    private LoginModel loginModel;
    private LoginViewListener viewListener;
    private Context context;

    public LoginPresenter(Context context,LoginViewListener viewListener) {
        this.context = context;
        this.viewListener = viewListener;
        loginModel = new LoginModel();
    }

    public void login(String accout, String code) {

        loginModel.login(new BaseSubscriber<UserCoach>(context) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.loginResult(false);
            }

            @Override
            public void onNext(UserCoach user) {
                viewListener.loginResult(true);
                App.getInstance().setUser(user);

            }
        }, accout, code);

    }

    public void getIdentify(final String phone, String imageCode){

        loginModel.getIdentify(new BaseSubscriber<String>(context) {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onGetIdetify(false,phone);
            }

            @Override
            public void onNext(String s) {
                  viewListener.onGetIdetify(true,phone);
            }
        },phone,imageCode);
    }

}
