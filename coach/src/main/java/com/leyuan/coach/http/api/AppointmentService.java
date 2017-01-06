package com.leyuan.coach.http.api;


import com.leyuan.coach.bean.AppointmentBean;
import com.leyuan.coach.bean.AppointmentDetailBean;
import com.leyuan.coach.bean.BaseBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


public interface AppointmentService {

    @GET("coachInfo/getMyCampaignList.json")
    Observable<BaseBean<List<AppointmentBean>>> getAppointments(@Query("coachId") String coachId,
                                                                @Query("type") String type,
                                                                @Query("page") int page);

    @GET("train/getDetailById.json")
    Observable<BaseBean<AppointmentDetailBean>> getAppointmentDetail(@Query("orderId") String orderId);

    @FormUrlEncoded
    @POST("coachInfo/updateCampaignStatus.json")
    Observable<BaseBean> updateAppointmentStatus(@Field("orderId") String orderId, @Field("type") String type);
}
