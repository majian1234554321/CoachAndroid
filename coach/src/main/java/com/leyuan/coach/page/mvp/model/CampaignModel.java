package com.leyuan.coach.page.mvp.model;

import com.leyuan.coach.bean.CampaignBean;
import com.leyuan.coach.bean.CampaignDetailBean;
import com.leyuan.coach.bean.PayOrderBean;
import com.leyuan.coach.http.RetrofitHelper;
import com.leyuan.coach.http.RxHelper;
import com.leyuan.coach.http.api.CampaignService;

import java.util.List;

import rx.Subscriber;

public class CampaignModel {
    private CampaignService campaignService;

    public CampaignModel() {
        campaignService = RetrofitHelper.createApi(CampaignService.class);
    }

    public void getCampaigns(Subscriber<List<CampaignBean>> subscriber, String type, int page) {
        campaignService.getCampaigns(type,page)
                .compose(RxHelper.<List<CampaignBean>>transform())
                .subscribe(subscriber);
    }

    public void getCampaignDetail(Subscriber<CampaignDetailBean> subscriber, String campaignId, String coachId) {
        campaignService.getCampaignDetail(campaignId,coachId)
                .compose(RxHelper.<CampaignDetailBean>transform())
                .subscribe(subscriber);
    }

    public void buyCampaign(Subscriber<PayOrderBean> subscriber, String campaignId, String coachId, String payType) {
        campaignService.buyCampaign(campaignId,coachId,payType)
                .compose(RxHelper.<PayOrderBean>transform())
                .subscribe(subscriber);
    }

    public void changePayType(Subscriber<PayOrderBean> subscriber,String orderId,String peyType){
        campaignService.changePayType(orderId,peyType)
                .compose(RxHelper.<PayOrderBean>transform())
                .subscribe(subscriber);
    }
}
