package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.mvp.presenter.AccountBalancePresenter;
import com.leyuan.coach.page.mvp.view.AccoutBalanceViewListener;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.UiManager;

/**
 * Created by user on 2016/12/28.
 */
public class MyMoneyActivity extends BaseActivity implements View.OnClickListener, AccoutBalanceViewListener {

    private CommonTitleLayout layoutTitle;
    private LinearLayout layoutRecentEarning;
    private LinearLayout layoutWithdrawRecord;
    private LinearLayout layoutMoreDetail;
    private TextView txtBalance;

    private AccountBalancePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        presenter = new AccountBalancePresenter(this);
        presenter.setAccoutBalanceViewListener(this);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        txtBalance = (TextView) findViewById(R.id.txt_balance);

        layoutTitle.setLeftIconListener(this);
        findViewById(R.id.bt_withdraw).setOnClickListener(this);
        findViewById(R.id.layout_recent_earning).setOnClickListener(this);
        findViewById(R.id.layout_withdraw_record).setOnClickListener(this);
        findViewById(R.id.layout_more_detail).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.bt_withdraw:
                UiManager.activityJump(this, WithDrawActivity.class);

                break;
            case R.id.layout_recent_earning:
                UiManager.activityJump(this, RecentEarningActivity.class);

                break;
            case R.id.layout_withdraw_record:
                UiManager.activityJump(this, WithDrawRecordActivity.class);

                break;
            case R.id.layout_more_detail:
                UiManager.activityJump(this, WithDrawRecordMoreActivity.class);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getBalance();
    }

    @Override
    public void onGetBalance(String s) {
        if (s != null) {
            txtBalance.setText(s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
