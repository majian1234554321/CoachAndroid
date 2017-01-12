package com.leyuan.coach.http.api;

import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.bean.RecentEarningResult;
import com.leyuan.coach.bean.WithdrawDetail;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2016/12/28.
 */

public interface AccountBalanceService {

    @GET("coachInfo/getUserIncome.json")
    Observable<BaseBean<String>> getBalance(@Query("coachId") String coachId);

    @GET("coachInfo/getIncome.json")
    Observable<BaseBean<String>> getRecentEarnings(@Query("coachId") String coachId);


    @GET("coachInfo/getPresentRecord.json")
    Observable<BaseBean<ArrayList<WithdrawDetail>>> getWithdrawRecord(@Query("coachId") String coachId, @Query("page") String page);

    @GET("coachInfo/getMorePresent.json")
    Observable<BaseBean<ArrayList<RecentEarningResult>>> getMoreWithdrawRecord(@Query("coachId") String coachId, @Query("page") String page);

    @FormUrlEncoded
    @POST("coachInfo/addUserMoney.json")
    Observable<BaseBean<Object>> withdraw(@Field("coachId") String coachId, @Field("aliId") String aliId,
                                          @Field("userName") String userName, @Field("money") String money);


    @GET("coachInfo/getIncome.json")
    Observable<BaseBean<RecentEarningResult>> getRecentEarning(@Query("coachId") String id, @Query("cashTime") String cashTime);

}
