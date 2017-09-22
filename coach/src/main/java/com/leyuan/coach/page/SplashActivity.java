package com.leyuan.coach.page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.leyuan.coach.bean.VersionInformation;
import com.leyuan.coach.page.mvp.presenter.SplashPresenter;
import com.leyuan.coach.page.mvp.presenter.VersionPresenter;
import com.leyuan.coach.page.mvp.view.AutoLoginViewListener;
import com.leyuan.coach.page.mvp.view.VersionViewListener;
import com.leyuan.coach.utils.SharePrefUtils;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.manager.VersionManager;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.leyuan.commonlibrary.widget.dialog.BaseDialog;
import com.leyuan.commonlibrary.widget.dialog.ButtonCancelListener;
import com.leyuan.commonlibrary.widget.dialog.ButtonOkListener;
import com.leyuan.commonlibrary.widget.dialog.DialogDoubleButton;
import com.leyuan.commonlibrary.widget.dialog.DialogSingleButton;


public class SplashActivity extends BaseActivity implements AutoLoginViewListener, VersionViewListener {

    private SplashPresenter presenter;
    private VersionPresenter versionPresenter;
    private boolean autoLoginSuccess;
    private VersionInformation versionInfomation;
    private boolean firstOpenApp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
        versionPresenter = new VersionPresenter(this, this);
        presenter = new SplashPresenter(this, this);
        firstOpenApp = SharePrefUtils.getIsFirstOpenApp(this);
        initData();
    }

    private void initData() {
        versionPresenter.getVersionInfo();
    }

    @Override
    public void onGetVersionInfo(VersionInformation versionInfomation) {
        this.versionInfomation = versionInfomation;
        if (versionInfomation != null && VersionManager.shouldUpdate(versionInfomation.getVersion(), this)) {
            showUpdateDialog(versionInfomation);
        } else {
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
                        startDownload(downloadUrl);
                    }
                }).show();


    }

    private void startDownload(String downloadUrl) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(downloadUrl);
            intent.setData(content_url);
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show(SplashActivity.this, "下载地址解析失败");
        }
    }

    private void showForceUpdateDialog(VersionInformation versionInformation) {
        final String downloadUrl = versionInformation.getApk();
        new DialogSingleButton(this).setContentDesc("新版本 V" + versionInformation.getVersion() + " 已发布,请下载")
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        startDownload(downloadUrl);
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
                    presenter.checkPermission();
//                    checkAutoStart();
//                    if (firstOpenApp) {
//                        Bundle bunble = new Bundle();
//                        bunble.putBoolean(ConstantString.AUTO_LOGIN_RESULT, autoLoginSuccess);
//                        UiManager.activityJump(SplashActivity.this, bunble, GuideActivity.class);
//                    } else {
//                        UiManager.activityJump(SplashActivity.this, MainActivity.class);
//                    }
//                    if (autoLoginSuccess) {
//                        UiManager.activityJump(SplashActivity.this, MainActivity.class);
//                    } else {
//                        UiManager.activityJump(SplashActivity.this, LoginActivity.class);
//                    }
//                    finish();
                    break;
            }
        }
    };

    @Override
    public void checkOver() {

        UiManager.activityJump(SplashActivity.this, HtmlFiveActivity.class);
        finish();

//        if (firstOpenApp) {
//            Bundle bunble = new Bundle();
//            bunble.putBoolean(ConstantString.AUTO_LOGIN_RESULT, autoLoginSuccess);
//            UiManager.activityJump(SplashActivity.this, bunble, GuideActivity.class);
//        } else if (autoLoginSuccess) {
//            UiManager.activityJump(SplashActivity.this, MainActivity.class);
//        } else {
//            UiManager.activityJump(SplashActivity.this, LoginActivity.class);
//        }
//        finish();

//
//        if (firstOpenApp) {
//            Bundle bunble = new Bundle();
//            bunble.putBoolean(ConstantString.AUTO_LOGIN_RESULT, autoLoginSuccess);
//            UiManager.activityJump(SplashActivity.this, bunble, GuideActivity.class);
//        } else {
//            UiManager.activityJump(SplashActivity.this, MainActivity.class);
//        }
//        finish();
    }

//    private void checkAutoStart() {
//        if (CheckAutoStartUtils.isNeedCheck(this)) {
//            new DialogDoubleButton(this)
//                    .setContentDesc("为了保证能及时收到代课听课通知，请进入设置把应用加入自启动白名单")
//                    .setBtnCancelListener(new ButtonCancelListener() {
//                        @Override
//                        public void onClick(BaseDialog dialog) {
//                            dialog.dismiss();
//                            presenter.checkPermission();
//                        }
//                    })
//                    .setBtnOkListener(new ButtonOkListener() {
//                        @Override
//                        public void onClick(BaseDialog dialog) {
//                            CheckAutoStartUtils.skipToAutoStartView(SplashActivity.this);
//                            dialog.dismiss();
//                            presenter.checkPermission();
//                        }
//                    }).show();
//        } else {
//            presenter.checkPermission();
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
