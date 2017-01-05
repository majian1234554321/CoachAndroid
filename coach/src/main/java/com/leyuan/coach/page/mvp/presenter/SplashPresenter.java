package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.mvp.model.LoginModel;
import com.leyuan.coach.page.mvp.view.AutoLoginViewListener;

/**
 * Created by user on 2017/1/4.
 */

public class SplashPresenter {

    private LoginModel loginModel;
    private AutoLoginViewListener viewListener;
    private Context context;

    public SplashPresenter(AutoLoginViewListener viewListener, Context context) {
        this.viewListener = viewListener;
        this.context = context;
        loginModel = new LoginModel();
    }

    public void autoLogin() {
        loginModel.autoLogin(new BaseSubscriber<UserCoach>(context) {
            @Override
            public void onNext(UserCoach userCoach) {
                App.getInstance().setUser(userCoach);
                viewListener.onAutoLoginResult(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                App.getInstance().setUser(null);
                viewListener.onAutoLoginResult(false);

            }
        });
    }
}
