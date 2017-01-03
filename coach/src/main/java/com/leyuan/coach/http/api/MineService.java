package com.leyuan.coach.http.api;

import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.bean.CoachInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2016/12/20.
 */

public interface MineService {

    @GET("coachInfo/getMyInfo.json")
    Observable<BaseBean<CoachInfo>>  getUserInfo(@Query("coachId") String id);
}
