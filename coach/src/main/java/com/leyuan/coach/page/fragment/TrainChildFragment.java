package com.leyuan.coach.page.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.CampaignBean;
import com.leyuan.coach.page.BaseFragment;
import com.leyuan.coach.page.adapter.CampaignAdapter;
import com.leyuan.coach.page.mvp.presenter.CampaignPresent;
import com.leyuan.coach.page.mvp.view.TrainChildViewListener;
import com.leyuan.coach.widget.SwitcherLayout;
import com.leyuan.coach.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.coach.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.coach.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.coach.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

public class TrainChildFragment extends BaseFragment implements TrainChildViewListener {
    public static final String FREE = "0";
    public static final String PAY = "1";

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CampaignPresent campaignPresent;

    private String type;
    private int currPage = 1;
    private List<CampaignBean> data;
    private CampaignAdapter campaignAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_train_child,container,false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getString("type");
        }
        campaignPresent =  new CampaignPresent(getContext(),this);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        campaignPresent.commonLoadData(switcherLayout,type);
    }

    private void initSwipeRefreshLayout(View view){
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getActivity(),refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                campaignPresent.pullToRefreshData(type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_campaign);
        data = new ArrayList<>();
        campaignAdapter = new CampaignAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(campaignAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                campaignPresent.requestMoreData(type,recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<CampaignBean> campaignList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(campaignList);
        campaignAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
