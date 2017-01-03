package com.leyuan.coach.http.api;

import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.bean.MessageInfo;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2017/1/3.
 */

public interface MessageService {

    @GET("coachMsg/getMsgList.json")
    Observable<BaseBean<ArrayList<MessageInfo>>> getMsgList(@Query("coachId") String id);
}
