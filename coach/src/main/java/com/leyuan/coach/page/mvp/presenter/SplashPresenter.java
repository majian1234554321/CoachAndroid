package com.leyuan.coach.page.mvp.presenter;

import android.Manifest;
import android.app.Activity;

import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.mvp.model.LoginModel;
import com.leyuan.coach.page.mvp.view.AutoLoginViewListener;
import com.leyuan.commonlibrary.manager.PermissionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/1/4.
 */

public class SplashPresenter {

    private LoginModel loginModel;
    private AutoLoginViewListener viewListener;
    private Activity context;
    private PermissionManager permissionManager;

    public SplashPresenter(AutoLoginViewListener viewListener, Activity context) {
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
                App.getInstance().exitLogin();
                viewListener.onAutoLoginResult(false);

            }
        });
    }

    public void checkPermission() {
        //        PermissionsUtil.checkAndRequestPermissions(this, null);
        Map<String, String> map = new HashMap<>();
        map.put(Manifest.permission.ACCESS_FINE_LOCATION, "请打开定位服务，以免造成上课无法正常签到");
        map.put(Manifest.permission.CALL_PHONE, "请打开电话服务，以正常使用应用");

        permissionManager = new PermissionManager(map, context, viewListener);
        permissionManager.checkPermissionList();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

