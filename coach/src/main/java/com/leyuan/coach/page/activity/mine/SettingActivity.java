package com.leyuan.coach.page.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.bean.VersionInformation;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.activity.account.LoginActivity;
import com.leyuan.coach.page.mvp.presenter.LoginPresenter;
import com.leyuan.coach.page.mvp.presenter.VersionPresenter;
import com.leyuan.coach.page.mvp.view.LoginViewListener;
import com.leyuan.coach.page.mvp.view.VersionViewListener;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.PermissionManager;
import com.leyuan.commonlibrary.manager.TelephoneManager;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.manager.VersionManager;
import com.leyuan.commonlibrary.util.DialogUtils;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.leyuan.commonlibrary.widget.dialog.BaseDialog;
import com.leyuan.commonlibrary.widget.dialog.ButtonCancelListener;
import com.leyuan.commonlibrary.widget.dialog.ButtonOkListener;
import com.leyuan.commonlibrary.widget.dialog.DialogDoubleButton;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by user on 2017/1/4.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, VersionViewListener, LoginViewListener, PermissionManager.OnCheckPermissionListener {
    private CommonTitleLayout layoutTitle;
    private RelativeLayout layoutAboutCoach;
    private RelativeLayout layoutUserAgreement;
    private RelativeLayout layoutVersionUpdate;
    private TextView txtVersion;
    private RelativeLayout layoutContactUs;
    private TextView txtContactPhone;
    private String contactUsPhone;

    VersionPresenter presenter;
    LoginPresenter presenterLogin;
    private PermissionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        presenter = new VersionPresenter(this, this);
        presenterLogin = new LoginPresenter(this, this);

        initView();
        initData();
    }

    private void initView() {
        txtVersion = (TextView) findViewById(R.id.txt_version);
        txtContactPhone = (TextView) findViewById(R.id.txt_contact_phone);
        findViewById(R.id.bt_exit_login).setOnClickListener(this);
        ((CommonTitleLayout) findViewById(R.id.layout_title)).setLeftIconListener(this);
        findViewById(R.id.layout_about_coach).setOnClickListener(this);
        findViewById(R.id.layout_user_agreement).setOnClickListener(this);
        findViewById(R.id.layout_version_update).setOnClickListener(this);
        findViewById(R.id.layout_contact_us).setOnClickListener(this);
    }

    private void initData() {

        UserCoach user = App.getInstance().getUser();
        if (user != null) {
            contactUsPhone = user.getContactUs();
        }
        txtVersion.setText("LV " + VersionManager.getVersionName(this));
        txtContactPhone.setText("" + contactUsPhone);

        Map<String, String> map = new HashMap<>();
        map.put(Manifest.permission.CALL_PHONE, "请打开电话服务，以正常使用应用");
        manager = new PermissionManager(map, this, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.layout_about_coach:
                UiManager.activityJump(this, CoachClientInfoActivity.class);
                break;
            case R.id.layout_user_agreement:
                UiManager.activityJump(this, UserAgreementActivity.class);
                break;
            case R.id.layout_version_update:
                presenter.getVersionInfo();
                DialogUtils.showDialog(this, getResources().getString(R.string.request_newest_version_info), false);
                break;
            case R.id.layout_contact_us:
                manager.checkPermissionList();
                break;
            case R.id.bt_exit_login:
                presenterLogin.loginOut();
                break;
        }
    }

    @Override
    public void onGetVersionInfo(VersionInformation versionInfomation) {
        DialogUtils.dismissDialog();
        if (versionInfomation == null)
            return;
        if (VersionManager.shouldUpdate(versionInfomation.getVersion(), this)) {
            showUpdateDialog(versionInfomation);
        } else {
            ToastUtil.showLong(this, "当前已是最新版本");
        }
    }

    private void showUpdateDialog(VersionInformation versionInfomation) {
        final String downloadUrl = versionInfomation.getApk();
        new DialogDoubleButton(this)
                .setContentDesc("最新版本 LV" + versionInfomation.getVersion())
                .setLeftButton("暂不")
                .setRightButton("立即更新")
                .setBtnCancelListener(new ButtonCancelListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
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
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void loginResult(boolean result) {

    }

    @Override
    public void onGetIdetify(boolean result, String mobile) {

    }

    @Override
    public void onLoginOut(boolean success) {
        if (success) {
            App.getInstance().exitLogin();
            UiManager.activityJump(this, LoginActivity.class);
            finish();
        }
    }

    @Override
    public void checkOver() {
        new DialogDoubleButton(this).setContentDesc("拨打电话")
                .setLeftButton("取消")
                .setRightButton("拨打")
                .setContentDesc("" + contactUsPhone)
                .setBtnCancelListener(new ButtonCancelListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        TelephoneManager.callImmediate(SettingActivity.this, contactUsPhone);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }
}
