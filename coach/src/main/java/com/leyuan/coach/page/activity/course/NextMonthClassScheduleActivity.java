package com.leyuan.coach.page.activity.course;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.leyuan.coach.R;

/**
 * Created by user on 2016/12/29.
 */
public class NextMonthClassScheduleActivity extends Activity implements View.OnClickListener {

    private FrameLayout layoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_month_class_schedule);

        layoutContainer = (FrameLayout) findViewById(R.id.layout_container);
        findViewById(R.id.img_left).setOnClickListener(this);
        findViewById(R.id.confirmed).setOnClickListener(this);
        findViewById(R.id.bt_unconfirmed).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.confirmed:
                findViewById(R.id.confirmed).setSelected(true);
                findViewById(R.id.bt_unconfirmed).setSelected(false);
                break;
            case R.id.bt_unconfirmed:
                findViewById(R.id.confirmed).setSelected(false);
                findViewById(R.id.bt_unconfirmed).setSelected(true);
                break;
        }
    }
}
