package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.WithdrawDetail;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.WithDrawRecordAdapter;
import com.leyuan.coach.page.mvp.presenter.WithDrawRecordPresenter;
import com.leyuan.coach.page.mvp.view.WithDrawRecordListener;
import com.leyuan.coach.widget.CommonTitleLayout;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/28.
 */
public class WithDrawRecordActivity extends BaseActivity implements WithDrawRecordListener {
    private CommonTitleLayout titleLayout;
    private RecyclerView recyclerView;
    private WithDrawRecordPresenter presenter;
    private WithDrawRecordAdapter adapter;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_record);

        presenter = new WithDrawRecordPresenter(this);
        presenter.setWithDrawRecordListener(this);

        titleLayout = (CommonTitleLayout) findViewById(R.id.title_layout);
        titleLayout.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new WithDrawRecordAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter.getWithdrawRecord(page);
    }

    @Override
    public void onGetWithDrawRecord(ArrayList<WithdrawDetail> withdrawDetails, int page) {
        if (page == 0) {
            adapter.refreshData(withdrawDetails);
        } else {
            adapter.addMoreData(withdrawDetails);
        }

    }
}
