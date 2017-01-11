package com.leyuan.coach.page.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.AppointmentBean;
import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.BaseLazyFragment;
import com.leyuan.coach.page.adapter.AppointmentAdapter;
import com.leyuan.coach.page.mvp.presenter.AppointmentPresent;
import com.leyuan.coach.page.mvp.view.AppointmentViewListener;
import com.leyuan.coach.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.coach.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.coach.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.coach.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

public class AllAppointmentFragment extends BaseLazyFragment implements AppointmentViewListener {
    public static final String ALL = "0";
    public static final String ORDER_CANCEL = "2";
    public static final String ORDER_CONFIRM = "3";
    public static final String ORDER_DELETE = "4";

    private View view;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout emptyLayout;

    private int currPage = 1;
    private List<AppointmentBean> data;
    private AppointmentAdapter appointmentAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String coachId;
    private AppointmentPresent present;

    @Override
    public View initView() {
        present = new AppointmentPresent(getContext(),this);
        coachId = String.valueOf(App.getInstance().getUser().getId());
        view =  LayoutInflater.from(getContext()).inflate(R.layout.fragment_appointment,null);
        emptyLayout = (LinearLayout) view.findViewById(R.id.ll_empty);
        initSwipeRefreshLayout();
        initRecyclerView();
        return view;
    }

    @Override
    public void initData() {
        currPage = 1;
        emptyLayout.setVisibility(View.GONE);
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        refreshLayout.setRefreshing(true);
        present.pullToRefreshData(coachId, ALL);
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(coachId,ALL);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_appointment);
        data = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(getContext());
        appointmentAdapter.setOrderHandleListener(new MySimpleOrderHandleListener());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(appointmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreData(recyclerView,coachId,ALL,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<AppointmentBean> appointmentBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(appointmentBeanList);
        appointmentAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void setUpdateOrderStatus(BaseBean baseBean) {
        if(baseBean.getCode() == 1){
            initData();
        }
        Toast.makeText(getContext(),baseBean.getMessage(),Toast.LENGTH_LONG).show();
    }

    private class MySimpleOrderHandleListener extends AppointmentAdapter.SimpleOrderHandleListener{
        @Override
        public void onRefreshOrderStatus() {
            initData();
        }

        @Override
        public void onCancelJoin(String orderId) {
            present.updateOrderStatus(orderId,ORDER_CANCEL);
        }

        @Override
        public void onConfirmJoin(String orderId) {
            present.updateOrderStatus(orderId,ORDER_CONFIRM);
        }

        @Override
        public void onDeleteOrder(String orderId) {
            present.updateOrderStatus(orderId,ORDER_DELETE);
        }
    }
}
