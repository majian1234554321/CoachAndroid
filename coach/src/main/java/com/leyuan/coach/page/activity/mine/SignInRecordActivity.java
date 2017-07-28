package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.SignRecordAdapter;
import com.leyuan.coach.page.mvp.presenter.SignPresenter;
import com.leyuan.coach.page.mvp.view.SignViewListener;
import com.leyuan.coach.widget.CommonEmptyLayout;
import com.leyuan.coach.widget.popupwindow.PopupWindowSignInMonth;
import com.leyuan.commonlibrary.util.MyDateUtils;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;

import static com.leyuan.coach.R.id.txt_title;


/**
 * Created by user on 2017/1/3.
 */
public class SignInRecordActivity extends BaseActivity implements View.OnClickListener, SignViewListener, PopupWindowSignInMonth.OnSignItemClickListener {

    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgChoose;
    private UltimateRecyclerView ultimateList;

    private SignRecordAdapter adapter;
    private SignPresenter presenter;
    private int page;
    private String currentMonth;

    private PopupWindowSignInMonth popup;
    private RelativeLayout layoutTitle;
    private CommonEmptyLayout emptyview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_record);

        initView();
        initData();
    }

    private void initView() {
        layoutTitle = (RelativeLayout) findViewById(R.id.layout_title);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        txtTitle = (TextView) findViewById(txt_title);
        imgChoose = (ImageView) findViewById(R.id.img_choose);
        emptyview = (CommonEmptyLayout) findViewById(R.id.empty_view);
        ultimateList = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        ultimateList.setHasFixedSize(true);
        ultimateList.setLayoutManager(new LinearLayoutManager(this));

//        View view = View.inflate(this, R.layout.custom_bottom_progressbar, null);
        adapter = new SignRecordAdapter(this);
//        adapter.setCustomLoadMoreView(view);
        ultimateList.setAdapter(adapter);

        ultimateList.setDefaultOnRefreshListener(refreshListener);
//        ultimateList.setLoadMoreView(view);
        ultimateList.reenableLoadmore();
        ultimateList.setOnLoadMoreListener(loadMoreListener);

        ultimateList.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_CLEAR_ALL);
//        ultimateList.reenableLoadmore();
    }

    private void initData() {
        presenter = new SignPresenter(this, this);
        presenter.getMonthList();
        popup = new PopupWindowSignInMonth(this, this);

        imgLeft.setOnClickListener(this);
        txtTitle.setOnClickListener(this);
        imgChoose.setOnClickListener(this);

    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            presenter.getSignInList(currentMonth, page);
        }
    };

    private UltimateRecyclerView.OnLoadMoreListener loadMoreListener = new UltimateRecyclerView.OnLoadMoreListener() {
        @Override
        public void loadMore(int itemsCount, int maxLastVisiblePosition) {
            page++;
            presenter.getSignInList(currentMonth, page);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.img_choose:
            case txt_title:
                popup.showAsDropDown(layoutTitle);
                break;
        }
    }

    @Override
    public void onGetSignList(ArrayList<ClassSchedule> arrayList, int page) {
//        ultimateList.setRefreshing(false);

        if (page > 1) {
            adapter.addData(arrayList);
        } else {
            adapter.refreshData(arrayList);
            if (arrayList == null || arrayList.isEmpty()) {
                emptyview.setVisibility(View.VISIBLE);
            } else {
                emptyview.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onGetMonthList(ArrayList<String> strings) {
        if (strings != null) {
            page = 1;
            currentMonth = strings.isEmpty() ? MyDateUtils.getCurrentMonth() : strings.get(0);
            presenter.getSignInList(currentMonth, page);
            txtTitle.setText(currentMonth);
            popup.setData(strings);
        }
    }

    @Override
    public void onMonthItemClicked(String month) {
        page = 1;
        currentMonth = month;
        presenter.getSignInList(currentMonth, page);
        txtTitle.setText(currentMonth);
    }
}
