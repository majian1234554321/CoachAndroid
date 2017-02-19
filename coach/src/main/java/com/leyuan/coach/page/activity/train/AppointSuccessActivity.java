package com.leyuan.coach.page.activity.train;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.MainActivity;
import com.leyuan.coach.page.activity.mine.AppointmentDetailActivity;
import com.leyuan.coach.widget.SimpleTitleBar;

public class AppointSuccessActivity extends BaseActivity implements View.OnClickListener {
    private SimpleTitleBar titleBar;
    private TextView tvTime;
    private TextView tvTrain;
    private TextView tvAppointment;
    private String time;
    private String orderId;

    public static void start(Context context, String trainTime, String orderId) {
        Intent starter = new Intent(context, AppointSuccessActivity.class);
        starter.putExtra("trainTime", trainTime);
        starter.putExtra("orderId", orderId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_success);
        if (getIntent() != null) {
            time = getIntent().getStringExtra("trainTime");
            orderId = getIntent().getStringExtra("orderId");
        }
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvTrain = (TextView) findViewById(R.id.tv_train);
        tvAppointment = (TextView) findViewById(R.id.tv_appointment);
        tvTime.setText(time);

        titleBar.setOnClickListener(this);
        tvTrain.setOnClickListener(this);
        tvAppointment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_train:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("tag", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.tv_appointment:
                AppointmentDetailActivity.start(this, orderId);
                break;
            default:
                break;
        }
    }
}
