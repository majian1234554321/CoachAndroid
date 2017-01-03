package com.leyuan.coach.http.api;

import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.bean.UserCoach;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface LoginService {

    @FormUrlEncoded
    @POST("coachLogin/getCoachUser.json")
    Observable<BaseBean<UserCoach>> login(@Field("phone") String phone, @Field("code") String code);

    @GET("code/createCode.json")
    Observable<BaseBean<String>> getImageIdentify(@Query("mobile") String account);

    @GET("coachLogin/sendMsgCode.json")
    Observable<BaseBean<String>> getIdentify(@Query("phone") String phone, @Query("userCode") String code);

}
