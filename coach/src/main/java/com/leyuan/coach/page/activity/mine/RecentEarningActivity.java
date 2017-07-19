package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.RecentEarningResult;
import com.leyuan.coach.config.ConstantString;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.RecentEarningAdapter;
import com.leyuan.coach.page.mvp.presenter.AccountBalancePresenter;
import com.leyuan.coach.page.mvp.view.RecentEarningViewListener;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.coach.widget.CommonEmptyLayout;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.util.MyDateUtils;


/**
 * Created by user on 2016/12/28.
 */
public class RecentEarningActivity extends BaseActivity implements RecentEarningViewListener {

    private CommonTitleLayout layoutTitle;
    private RecyclerView recyclerView;
    private RecentEarningAdapter adapter;
    private AccountBalancePresenter presenter;
    private String month;
    private CommonEmptyLayout empty_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_earning);
        presenter = new AccountBalancePresenter(this);
        presenter.setRecentEarningViewListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            month = bundle.getString(ConstantString.MONTHS, MyDateUtils.getLastMonth());

            LogUtil.i("RecentEarningActivity "," month = " + month);
        }
        if(TextUtils.isEmpty(month)){
            month = MyDateUtils.getLastMonth();
        }

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        empty_view = (CommonEmptyLayout) findViewById(R.id.empty_view);

        initView();
        initData();
    }

    private void initView() {

        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layoutTitle.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiManager.activityJump(RecentEarningActivity.this, WithDrawRecordMoreActivity.class);
            }
        });

    }

    private void initData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new RecentEarningAdapter(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        presenter.getRecentEarning(month);
    }

    @Override
    public void onGetRecentEarning(RecentEarningResult recentEaringResult) {
        if (recentEaringResult != null) {
            adapter.refreshData(recentEaringResult);
            empty_view.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
