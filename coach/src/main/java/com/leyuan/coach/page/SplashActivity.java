package com.leyuan.coach.page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.VersionInformation;
import com.leyuan.coach.page.activity.account.LoginActivity;
import com.leyuan.coach.page.mvp.presenter.SplashPresenter;
import com.leyuan.coach.page.mvp.presenter.VersionPresenter;
import com.leyuan.coach.page.mvp.view.AutoLoginViewListener;
import com.leyuan.coach.page.mvp.view.VersionViewListener;
import com.leyuan.coach.widget.dialog.BaseDialog;
import com.leyuan.coach.widget.dialog.ButtonCancelListener;
import com.leyuan.coach.widget.dialog.ButtonOkListener;
import com.leyuan.coach.widget.dialog.DialogDoubleButton;
import com.leyuan.coach.widget.dialog.DialogSingleButton;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.manager.VersionManager;


public class SplashActivity extends BaseActivity implements AutoLoginViewListener, VersionViewListener {

    private SplashPresenter presenter;
    private VersionPresenter versionPresenter;
    private boolean autoLoginSuccess;
    private VersionInformation versionInfomation;

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


    }

    private void initData() {

        //jump version check
//        if (App.getInstance().isLogin()) {
//            UiManager.activityJump(SplashActivity.this, MainActivity.class);
//            finish();
//        } else {
//            UiManager.activityJump(SplashActivity.this, LoginActivity.class);
//            finish();
//        }

        versionPresenter.getVersionInfo();


    }

    @Override
    public void onGetVersionInfo(VersionInformation versionInfomation) {
        this.versionInfomation = versionInfomation;
        if (versionInfomation != null && VersionManager.shouldUpdate(versionInfomation.getVersion(), this)) {
            showUpdateDialog(versionInfomation);
        } else {
//            ToastUtil.show(SplashActivity.this, "获取版本失败，请检查网络");
            if (App.getInstance().isLogin()) {
                //先自动登录
                presenter.autoLogin();
            } else {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(1, 2000);
            }
        }

    }

    private void showUpdateDialog(VersionInformation versionInformation) {
        if (versionInformation.getIsUpdate() == 1 || VersionManager.mustUpdate(versionInformation.getVersion(), this)) {
            showForceUpdateDialog(versionInformation);
        } else {
            showNormalUpdateDialog(versionInformation);
        }
    }

    private void showNormalUpdateDialog(VersionInformation versionInformation) {
        final String downloadUrl = versionInformation.getApk();
        new DialogDoubleButton(this).setContentDesc("新版本 V" + versionInformation.getVersion() + " 已发布,请下载")
                .setBtnCancelListener(new ButtonCancelListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        if (App.getInstance().isLogin()) {
                            //先自动登录
                            presenter.autoLogin();
                        } else {
                            mHandler.removeCallbacksAndMessages(null);
                            mHandler.sendEmptyMessageDelayed(1, 2000);
                        }

                        dialog.dismiss();

                    }
                })
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        //start download
                        Uri uri = Uri.parse(downloadUrl);
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);
                    }
                }).show();


    }

    private void showForceUpdateDialog(VersionInformation versionInformation) {
        final String downloadUrl = versionInformation.getApk();
        new DialogSingleButton(this).setContentDesc("新版本 V" + versionInformation.getVersion() + " 已发布,请下载")
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        //start download
                        Uri uri = Uri.parse(downloadUrl);
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);
                    }
                }).show();
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

                    if (autoLoginSuccess) {
                        UiManager.activityJump(SplashActivity.this, MainActivity.class);
                    } else {
                        UiManager.activityJump(SplashActivity.this, LoginActivity.class);
                    }
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
