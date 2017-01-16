package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.manager.VersionManager;

/**
 * Created by user on 2017/1/4.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private CommonTitleLayout layoutTitle;
    private RelativeLayout layoutAboutCoach;
    private RelativeLayout layoutUserAgreement;
    private RelativeLayout layoutVersionUpdate;
    private TextView txtVersion;
    private RelativeLayout layoutContactUs;
    private TextView txtContactPhone;
    private String contactUsPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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

                break;
            case R.id.layout_contact_us:

                break;
            case R.id.bt_exit_login:
                break;
        }
    }
}
