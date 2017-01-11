package com.leyuan.coach.page.activity.train;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.MainActivity;
import com.leyuan.coach.page.activity.mine.AppointmentActivity;
import com.leyuan.coach.widget.SimpleTitleBar;

public class AppointSuccessActivity extends BaseActivity implements View.OnClickListener {
    private SimpleTitleBar titleBar;
    private TextView tvTime;
    private TextView tvHome;
    private TextView tvAppointment;
    private String time;

    public static void start(Context context,String trainTime) {
        Intent starter = new Intent(context, AppointSuccessActivity.class);
        starter.putExtra("trainTime",trainTime);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_success);
        if(getIntent() != null){
            time = getIntent().getStringExtra("trainTime");
        }
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvHome = (TextView) findViewById(R.id.tv_home);
        tvAppointment = (TextView) findViewById(R.id.tv_appointment);
        tvTime.setText(time);

        titleBar.setOnClickListener(this);
        tvHome.setOnClickListener(this);
        tvAppointment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar:
                finish();
                break;
            case R.id.tv_home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_appointment:
                startActivity(new Intent(this, AppointmentActivity.class));
                break;
            default:
                break;
        }
    }
}
