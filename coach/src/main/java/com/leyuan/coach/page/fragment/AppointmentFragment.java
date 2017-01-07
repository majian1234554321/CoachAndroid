package com.leyuan.coach.page.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.AppointmentBean;
import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.BaseFragment;
import com.leyuan.coach.page.adapter.AppointmentAdapter;
import com.leyuan.coach.page.mvp.presenter.AppointmentPresent;
import com.leyuan.coach.page.mvp.view.AppointmentViewListener;
import com.leyuan.coach.widget.SwitcherLayout;
import com.leyuan.coach.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.coach.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.coach.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.coach.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

public class AppointmentFragment extends BaseFragment implements AppointmentViewListener, AppointmentAdapter.OrderHandleListener {
    public static final String ALL = "0";
    public static final String JOINED = "1";
    public static final String UN_JOIN = "3";
    public static final String ORDER_CANCEL = "2";
    public static final String ORDER_CONFIRM = "3";
    public static final String ORDER_DELETE = "4";

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<AppointmentBean> data;
    private AppointmentAdapter appointmentAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String coachId;
    private String type;
    private AppointmentPresent present;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        present = new AppointmentPresent(getContext(),this);
        coachId = String.valueOf(App.getInstance().getUser().getId());
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getString("type");
        }
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        present.commonLoadData(switcherLayout,coachId,type);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(),refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(refreshLayout,coachId,type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_appointment);
        data = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(getContext());
        appointmentAdapter.setOrderHandleListener(this);
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
                present.requestMoreData(recyclerView,coachId,type,pageSize,currPage);
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
        switcherLayout.showEmptyLayout();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void onDeleteOrder(String orderId) {
        present.updateOrderStatus(orderId,ORDER_DELETE);
    }

    @Override
    public void onConfirmJoin(String orderId) {
        present.updateOrderStatus(orderId, ORDER_CONFIRM);
    }

    @Override
    public void onCancelJoin(String orderId) {
        present.updateOrderStatus(orderId, ORDER_CANCEL);
    }

    @Override
    public void setUpdateOderStatus(BaseBean baseBean) {
        if(baseBean.getCode() == 1){
            refreshLayout.setRefreshing(true);
            present.pullToRefreshData(refreshLayout,coachId,type);
            Toast.makeText(getContext(),"操作成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"操作失败",Toast.LENGTH_LONG).show();
        }
    }
}
