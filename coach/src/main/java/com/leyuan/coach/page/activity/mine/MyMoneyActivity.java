package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.UiManager;

/**
 * Created by user on 2016/12/28.
 */
public class MyMoneyActivity extends BaseActivity implements View.OnClickListener {

    private CommonTitleLayout layoutTitle;
    private LinearLayout layoutRecentEarning;
    private LinearLayout layoutWithdrawRecord;
    private LinearLayout layoutMoreDetail;
    private TextView txtBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        txtBalance = (TextView) findViewById(R.id.txt_balance);

        findViewById(R.id.bt_withdraw).setOnClickListener(this);
        findViewById(R.id.layout_recent_earning).setOnClickListener(this);
        findViewById(R.id.layout_withdraw_record).setOnClickListener(this);
        findViewById(R.id.layout_more_detail).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_withdraw:
                UiManager.activityJump(this,WithDrawActivity.class);

                break;
            case R.id.layout_recent_earning:
                UiManager.activityJump(this,RecentEarningActivity.class);

                break;
            case R.id.layout_withdraw_record:
                UiManager.activityJump(this,WithDrawRecordActivity.class);

                break;
            case R.id.layout_more_detail:
                UiManager.activityJump(this,WithDrawRecordMoreActivity.class);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
