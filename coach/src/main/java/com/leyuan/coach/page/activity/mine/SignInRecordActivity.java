package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.SignRecordAdapter;
import com.leyuan.coach.page.mvp.presenter.SignPresenter;
import com.leyuan.coach.page.mvp.view.SignViewListener;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/3.
 */
public class SignInRecordActivity extends BaseActivity implements View.OnClickListener, SignViewListener {

    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgChoose;
    private UltimateRecyclerView ultimateList;
    private SignRecordAdapter adapter;
    private SignPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_record);
        presenter = new SignPresenter(this, this);

        initView();
        initData();
    }

    private void initView() {
        imgLeft = (ImageView) findViewById(R.id.img_left);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imgChoose = (ImageView) findViewById(R.id.img_choose);
        ultimateList = (UltimateRecyclerView) findViewById(R.id.ultimate_list);
        ultimateList.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SignRecordAdapter(this);
        ultimateList.setAdapter(adapter);

        ultimateList.setDefaultOnRefreshListener(refreshListener);
        ultimateList.setLoadMoreView(null);
        ultimateList.reenableLoadmore();
        ultimateList.setOnLoadMoreListener(loadMoreListener);

    }

    private void initData() {
        imgLeft.setOnClickListener(this);
        presenter.getSignInList("2017-01");

    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

        }
    };

    private UltimateRecyclerView.OnLoadMoreListener loadMoreListener = new UltimateRecyclerView.OnLoadMoreListener() {
        @Override
        public void loadMore(int itemsCount, int maxLastVisiblePosition) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.txt_title:
                break;
            case R.id.img_choose:
                break;

        }
    }

    @Override
    public void onGetSignList(ArrayList<ClassSchedule> arrayList) {
        adapter.refreshData(arrayList);
    }
}
