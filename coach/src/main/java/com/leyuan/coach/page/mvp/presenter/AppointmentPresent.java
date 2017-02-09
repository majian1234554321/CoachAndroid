package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.coach.bean.AppointmentBean;
import com.leyuan.coach.bean.AppointmentDetailBean;
import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.http.subscriber.CommonSubscriber;
import com.leyuan.coach.http.subscriber.ProgressSubscriber;
import com.leyuan.coach.http.subscriber.RequestMoreSubscriber;
import com.leyuan.coach.page.mvp.model.AppointmentModel;
import com.leyuan.coach.page.mvp.view.AppointmentDetailViewListener;
import com.leyuan.coach.page.mvp.view.AppointmentViewListener;
import com.leyuan.coach.widget.SwitcherLayout;

import java.util.List;

public class AppointmentPresent  {
    private Context context;
    private AppointmentModel appointmentModel;

    //预约列表View层
    private AppointmentViewListener appointmentViewListener;

    //预约详情View层
    private AppointmentDetailViewListener appointmentDetailViewListener;

    public AppointmentPresent(Context context, AppointmentViewListener view) {
        this.context = context;
        this.appointmentViewListener = view;
        if(appointmentModel == null){
            appointmentModel = new AppointmentModel();
        }
    }

    public AppointmentPresent(Context context, AppointmentDetailViewListener view) {
        this.context = context;
        this.appointmentDetailViewListener = view;
        if(appointmentModel == null){
            appointmentModel = new AppointmentModel();
        }
    }

    public void commonLoadData(final SwitcherLayout switcherLayout, String coachId,String type) {
        appointmentModel.getAppointments(new CommonSubscriber<List<AppointmentBean>>(switcherLayout,context) {
            @Override
            public void onNext(List<AppointmentBean> appointmentBeanList) {
                if(appointmentBeanList != null && !appointmentBeanList.isEmpty()){
                    switcherLayout.showContentLayout();
                    appointmentViewListener.updateRecyclerView(appointmentBeanList);
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },coachId,type,1);
    }


    public void pullToRefreshData(String coachId, String type) {
        appointmentModel.getAppointments(new BaseSubscriber<List<AppointmentBean>>(context) {
            @Override
            public void onNext(List<AppointmentBean> appointmentBeanList) {
                if(appointmentBeanList != null && !appointmentBeanList.isEmpty()) {
                    appointmentViewListener.updateRecyclerView(appointmentBeanList);
                }else {
                    appointmentViewListener.showEmptyView();
                }
            }
        },coachId,type,1);
    }

    public void requestMoreData(RecyclerView recyclerView, String coachId, String type, final int pageSize, final int page) {
        appointmentModel.getAppointments(new RequestMoreSubscriber<List<AppointmentBean>>(context,recyclerView,pageSize) {
            @Override
            public void onNext(List<AppointmentBean> appointmentBeanList) {
                if(appointmentBeanList != null && !appointmentBeanList.isEmpty()){
                    appointmentViewListener.updateRecyclerView(appointmentBeanList);
                }
                if(appointmentBeanList == null || appointmentBeanList.size() < pageSize){
                    appointmentViewListener.showEndFooterView();
                }
            }
        },coachId,type,page);
    }


    public void getAppointmentDetail(final SwitcherLayout switcherLayout, String orderId) {
        appointmentModel.getAppointmentDetail(new CommonSubscriber<AppointmentDetailBean>(switcherLayout,context) {
            @Override
            public void onNext(AppointmentDetailBean appointmentDetailBean) {
                if(appointmentDetailBean != null && appointmentDetailBean.getCampaign() != null){
                    switcherLayout.showContentLayout();
                    appointmentDetailViewListener.setAppointmentDetail(appointmentDetailBean);
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },orderId);
    }

    public void getAppointmentDetail(String orderId) {
        appointmentModel.getAppointmentDetail(new BaseSubscriber<AppointmentDetailBean>(context) {
            @Override
            public void onNext(AppointmentDetailBean appointmentDetailBean) {
                if(appointmentDetailBean != null && appointmentDetailBean.getCampaign() != null) {
                    appointmentDetailViewListener.setAppointmentDetail(appointmentDetailBean);
                }
            }
        },orderId);
    }


    public void updateOrderStatus(String oderId,String type){
        appointmentModel.updateAppointmentStatus(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(appointmentViewListener != null) {
                    appointmentViewListener.setUpdateOrderStatus(baseBean);
                }
                if(appointmentDetailViewListener != null) {
                    appointmentDetailViewListener.setUpdateOrderStatus(baseBean);
                }
            }
        },oderId,type);
    }
}
