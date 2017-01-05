package com.leyuan.coach.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.VersionInfomation;
import com.leyuan.coach.page.activity.account.LoginActivity;
import com.leyuan.coach.page.mvp.presenter.SplashPresenter;
import com.leyuan.coach.page.mvp.presenter.VersionPresenter;
import com.leyuan.coach.page.mvp.view.AutoLoginViewListener;
import com.leyuan.coach.page.mvp.view.VersionViewListener;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.manager.VersionManager;
import com.leyuan.commonlibrary.util.ToastUtil;


public class SplashActivity extends BaseActivity implements AutoLoginViewListener, VersionViewListener {

    private SplashPresenter presenter;
    private VersionPresenter versionPresenter;
    private boolean autoLoginSuccess;
    private VersionInfomation versionInfomation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        versionPresenter = new VersionPresenter(this, this);
        presenter = new SplashPresenter(this, this);

        initView();
        initData();
    }

    private void initView() {

        if (App.getInstance().isLogin()) {
            UiManager.activityJump(SplashActivity.this, MainActivity.class);
        } else {
            UiManager.activityJump(SplashActivity.this, LoginActivity.class);
        }
    }

    private void initData() {
//        versionPresenter.getVersionInfo();


    }

    @Override
    public void onGetVersionInfo(VersionInfomation versionInfomation) {
        this.versionInfomation = versionInfomation;
        if (versionInfomation != null) {
            if (App.getInstance().isLogin()) {
                presenter.autoLogin();
            } else if (VersionManager.shouldUpdate(versionInfomation.getVersion(), SplashActivity.this)) {

                showUpdateDialog();
            } else {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(1, 2000);
            }

        } else {
            ToastUtil.show(SplashActivity.this, "获取版本失败，请检查网络");
        }

    }

    private void showUpdateDialog() {

    }

    @Override
    public void onAutoLoginResult(boolean success) {
        this.autoLoginSuccess = success;
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(1, 2000);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    UiManager.activityJump(SplashActivity.this, LoginActivity.class);
//                    if (VersionManager.shouldUpdate(versionInfomation.getVersion(), SplashActivity.this)) {
//                        showUpdateDialog();
//                        return;
//                    }
//
//                    if (autoLoginSuccess) {
//                        UiManager.activityJump(SplashActivity.this, MainActivity.class);
//                    } else {
//                        UiManager.activityJump(SplashActivity.this, LoginActivity.class);
//                    }
                    finish();

                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
