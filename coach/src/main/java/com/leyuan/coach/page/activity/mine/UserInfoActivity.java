package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.widget.CommonTitleLayout;


//import com.leyuan.coach.R;


/**
 * Created by user on 2016/12/20.
 */
public class UserInfoActivity extends BaseActivity {

    private CommonTitleLayout titleLayout;
    private SimpleDraweeView imgAvatar;
    private TextView txtName;
    private TextView txtGender;
    private TextView txtLevel;
    private TextView txtServiceType;
    private TextView txtBirthday;
    private TextView txtNumber;
    private TextView txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();
        initData();
    }

    private void initView() {
        titleLayout = (CommonTitleLayout) findViewById(R.id.title_layout);
        imgAvatar = (SimpleDraweeView) findViewById(R.id.img_avatar);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtGender = (TextView) findViewById(R.id.txt_gender);
        txtLevel = (TextView) findViewById(R.id.txt_level);
        txtServiceType = (TextView) findViewById(R.id.txt_service_type);
        txtBirthday = (TextView) findViewById(R.id.txt_birthday);
        txtNumber = (TextView) findViewById(R.id.txt_number);
        txtPhone = (TextView) findViewById(R.id.txt_phone);
    }

    private void initData() {
        titleLayout.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        UserCoach user = App.getInstance().getUser();
        if (user == null) {
            return;
        }
        imgAvatar.setImageURI(user.getAvatar());
        txtName.setText("" + user.getName());
        txtGender.setText("" + user.getGender());
        txtLevel.setText("LV" + user.getLevel());
        txtServiceType.setText("" + user.getKindName());
        txtBirthday.setText(user.getBirthday() == null ? "" : user.getBirthday());
        txtNumber.setText("" + user.getCarded());
        txtPhone.setText("" + user.getContact());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
