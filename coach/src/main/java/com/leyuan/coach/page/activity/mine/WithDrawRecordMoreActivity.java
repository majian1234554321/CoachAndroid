package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.RecentEarningResult;
import com.leyuan.coach.config.ConstantString;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.EarningDetailAdapter;
import com.leyuan.coach.page.mvp.presenter.WithDrawRecordPresenter;
import com.leyuan.coach.page.mvp.view.WithDrawRecordMoreListener;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.UiManager;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/28.
 */
public class WithDrawRecordMoreActivity extends BaseActivity implements WithDrawRecordMoreListener, EarningDetailAdapter.OnItemClickListener {


    private CommonTitleLayout titleLayout;
    private RecyclerView recyclerView;
    private WithDrawRecordPresenter presenter;
    private EarningDetailAdapter adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_more_record);

        presenter = new WithDrawRecordPresenter(this);
        presenter.setWithDrawRecordMoreListener(this);

        titleLayout = (CommonTitleLayout) findViewById(R.id.title_layout);
        titleLayout.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new EarningDetailAdapter(this);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter.getMoreWithdrawRecord(page);
    }

    @Override
    public void onGetMoreWithDrawRecord(ArrayList<RecentEarningResult> earningDetails, int page) {
        if (page == 1) {
            adapter.refreshData(earningDetails);
        } else {
            adapter.addMoreData(earningDetails);
        }

    }

    @Override
    public void onClick(String month) {
        Bundle bundle = new Bundle();
        bundle.putString(ConstantString.MONTHS, month);
        UiManager.activityJump(this, bundle, RecentEarningActivity.class);
    }
}
