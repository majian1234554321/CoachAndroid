package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.coach.bean.CampaignBean;
import com.leyuan.coach.bean.CampaignDetailBean;
import com.leyuan.coach.bean.PayOrderBean;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.http.subscriber.CommonSubscriber;
import com.leyuan.coach.http.subscriber.ProgressSubscriber;
import com.leyuan.coach.http.subscriber.RequestMoreSubscriber;
import com.leyuan.coach.page.mvp.model.CampaignModel;
import com.leyuan.coach.page.mvp.view.AppointTrainListener;
import com.leyuan.coach.page.mvp.view.AppointmentDetailViewListener;
import com.leyuan.coach.page.mvp.view.TrainChildViewListener;
import com.leyuan.coach.page.mvp.view.TrainDetailViewListener;
import com.leyuan.coach.widget.SwitcherLayout;

import java.util.List;

public class CampaignPresent {
    private Context context;
    private CampaignModel campaignModel;

    private TrainChildViewListener trainViewListener;             //活动列表
    private TrainDetailViewListener trainDetailViewListener;      //活动详情
    private AppointTrainListener appointTrainListener;            //活动预约
    private AppointmentDetailViewListener appointmentDetailViewListener;    //预约详情

    public CampaignPresent(Context context,AppointTrainListener view) {
        this.context = context;
        this.appointTrainListener = view;
        if(campaignModel == null){
            campaignModel = new CampaignModel();
        }
    }

    public CampaignPresent(Context context, TrainChildViewListener view) {
        this.context = context;
        this.trainViewListener = view;
        if(campaignModel == null){
            campaignModel = new CampaignModel();
        }
    }

    public CampaignPresent(Context context, TrainDetailViewListener view) {
        this.context = context;
        this.trainDetailViewListener = view;
        if(campaignModel == null){
            campaignModel = new CampaignModel();
        }
    }

    public CampaignPresent(Context context, AppointmentDetailViewListener view) {
        this.context = context;
        this.appointmentDetailViewListener = view;
        if(campaignModel == null){
            campaignModel = new CampaignModel();
        }
    }

    public void commonLoadData(final SwitcherLayout switcherLayout, String type) {
        campaignModel.getCampaigns(new CommonSubscriber<List<CampaignBean>>(switcherLayout) {
            @Override
            public void onNext(List<CampaignBean> campaignList) {
                if(campaignList != null && !campaignList.isEmpty()){
                    switcherLayout.showContentLayout();
                    trainViewListener.updateRecyclerView(campaignList);
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },type,1);
    }


    public void pullToRefreshData(String type) {
        campaignModel.getCampaigns(new BaseSubscriber<List<CampaignBean>>(context) {
            @Override
            public void onNext(List<CampaignBean> campaignList) {
                if(campaignList != null && !campaignList.isEmpty()){
                    trainViewListener.updateRecyclerView(campaignList);
                }
            }
        },type,1);
    }


    public void requestMoreData(String type,RecyclerView recyclerView, final int pageSize, int page) {
        campaignModel.getCampaigns(new RequestMoreSubscriber<List<CampaignBean>>(context,recyclerView,pageSize) {
            @Override
            public void onNext(List<CampaignBean> campaignBeanList) {
                if(campaignBeanList != null && !campaignBeanList.isEmpty()){
                    trainViewListener.updateRecyclerView(campaignBeanList);
                }
                //没有更多数据了显示到底提示
                if( campaignBeanList == null || campaignBeanList.size() < pageSize){
                    trainViewListener.showEndFooterView();
                }
            }
        },type,page);
    }

    public void getCampaignDetail(final SwitcherLayout switcherLayout, String campaignId, String coachId ){
        campaignModel.getCampaignDetail(new CommonSubscriber<CampaignDetailBean>(switcherLayout) {
            @Override
            public void onNext(CampaignDetailBean campaignDetailBean) {
                if(campaignDetailBean != null){
                    switcherLayout.showContentLayout();
                    trainDetailViewListener.setCampaignDetail(campaignDetailBean);
                }
            }
        },campaignId,coachId);
    }


   public void buyCampaign(String campaignId,String courseId,String payType){
       campaignModel.buyCampaign(new ProgressSubscriber<PayOrderBean>(context) {
           @Override
           public void onNext(PayOrderBean payOrderBean) {
               appointTrainListener.setPayResult(payOrderBean);
           }
       },campaignId,courseId,payType);
    }

    public void changePayType(String orderId,String payType){
        campaignModel.changePayType(new ProgressSubscriber<PayOrderBean>(context) {
            @Override
            public void onNext(PayOrderBean payOrderBean) {
                appointmentDetailViewListener.setChangePayType(payOrderBean);
            }
        },orderId,payType);
    }
}
