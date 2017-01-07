package com.leyuan.coach.page.activity.train;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.AppointUserBean;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.List;


public class AppointmentUserActivity extends BaseActivity {
    private ImageView ivBack;
    private RecyclerView rvUser;
    private List<AppointUserBean> data;

    public static void start(Context context, List<AppointUserBean> userList) {
        Intent starter = new Intent(context, AppointmentUserActivity.class);
        starter.putParcelableArrayListExtra("userList",(ArrayList)userList);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_user);
        if(getIntent() != null){
            data = getIntent().getParcelableArrayListExtra("userList");
        }
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rvUser = (RecyclerView) findViewById(R.id.rv_user);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        UserAdapter userAdapter = new UserAdapter(this);
        rvUser.setAdapter(userAdapter);
        userAdapter.setData(data);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

