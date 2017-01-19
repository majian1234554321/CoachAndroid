package com.leyuan.coach.http.api;

import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.bean.ClassSchedule;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2017/1/3.
 */

public interface SignService {

    @GET("course/getSignInList.json")
    Observable<BaseBean<ArrayList<ClassSchedule>>> getSignInList(@Query("coachId") String coachId, @Query("signTime") String signTime,@Query("page") String page);

    @GET("course/getMonthList.json")
    Observable<BaseBean<ArrayList<String>>> getMonthList();
}
