package com.leyuan.coach.http.api;

import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.bean.VersionInformation;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by user on 2017/1/4.
 */

public interface VersionService {

    @GET("coachInfo/getAppUpdate.json")
    Observable<BaseBean<VersionInformation>> getVersionInfo();
}
