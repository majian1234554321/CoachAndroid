package com.leyuan.coach.http.api;


import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.bean.CampaignBean;
import com.leyuan.coach.bean.CampaignDetailBean;
import com.leyuan.coach.bean.PayOrderBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


public interface CampaignService {

    @GET("train/getTrainList.json")
    Observable<BaseBean<List<CampaignBean>>> getCampaigns(@Query("type") String type, @Query("page") int page);

    @GET("train/getCampaignDetail.json")
    Observable<BaseBean<CampaignDetailBean>> getCampaignDetail(@Query("campaignId") String campaignId,
                                                               @Query("coachId") String coachId);

    @FormUrlEncoded
    @POST("train/addCampaign.json")
    Observable<BaseBean<PayOrderBean>> buyCampaign(@Field("campaignId") String campaignId,
                                                                 @Field("coachId") String coachId,
                                                                      @Field("payType") String payType);

    @GET("train/getChangePay.json")
    Observable<BaseBean<PayOrderBean>> changePayType(@Query("orderId") String orderId,
                                                               @Query("payType") String payType);
}
